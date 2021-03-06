package br.com.randomthings.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.randomthings.domain.Client;
import br.com.randomthings.domain.Contact;
import br.com.randomthings.domain.CreditCard;
import br.com.randomthings.domain.DeliveryAddress;
import br.com.randomthings.domain.Gender;
import br.com.randomthings.domain.Role;
import br.com.randomthings.domain.TelephoneType;
import br.com.randomthings.domain.User;
import br.com.randomthings.dto.validation.ClientPasswordEquals;
import br.com.randomthings.utils.SystemVariable;
import lombok.Data;

@Data
@ClientPasswordEquals
@Component
public class ClientDTO extends AbstractDTO<Client> {
	
	@NotBlank(message = "O campo nome é obrigatório.")
	@NotNull(message = "O campo nome é obrigatório.")
	@Length(min = 3, max = 100, message = "O campo nome deve ter entre 3 e 100 caracteres")
	private String firstName;
	
	@NotBlank(message = "O campo sobrenome é obrigatório.")
	@NotNull(message = "O campo sobrenome é obrigatório.")
	@Length(min = 5, max = 100, message = "O campo sobrenome deve ter entre 5 e 100 caracteres")
	private String lastName;
	
	@Email(message="O formato do email está incorreto")
	@NotBlank(message = "O campo email é obrigatório.")
	@NotNull(message = "O campo email é obrigatório.")
	private String email;
	
	@NotNull(message="O campo genero é obrigatório")
	private Gender gender;
	
	@CPF(message="CPF invalido")
	@NotBlank(message = "O campo CPF é obrigatório.")
	@NotNull(message = "O campo CPF é obrigatório.")
	private String cpf;
	
	@NotNull(message = "A data de nascimento é obrigatória")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@NotBlank(message = "O campo telefone é obrigatório.")
	@NotNull(message = "O campo telefone é obrigatório.")
	@Length(min = 11, max = 12, message = "O campo telefone deve ter entre 11 e 12 numeros")
	private String phone;
	
	@NotNull(message="O campo tipo de telefone é obrigatório")
	private TelephoneType telephoneType;
	
	@NotBlank(message = "O campo senha é obrigatório.")
	@NotNull(message = "O campo senha é obrigatório.")
	private String password;
	
	@JsonProperty(access = Access.READ_ONLY)
	private List<Role> roles;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "O campo confirmação de senha é obrigatório.")
	@NotNull(message = "O campo confirmação de senha é obrigatório.")
	private String confirmPassword;
	
	private List<DeliveryAddressDTO> deliveryAddress;
	
	private List<CreditCardDTO> cards;
	
	private void SetDeliveryAddress(Set<DeliveryAddress> set) {
		this.deliveryAddress = new ArrayList<DeliveryAddressDTO>();
		for(DeliveryAddress address: set) {
			this.deliveryAddress.add((DeliveryAddressDTO) new DeliveryAddressDTO().from(address));
		}
	}
	
	private void SetCreditCards(Set<CreditCard> set) {
		this.cards = new ArrayList<CreditCardDTO>();
		for(CreditCard card: set) {
			if(card.getStatus() == true)
				this.cards.add((CreditCardDTO) new CreditCardDTO().from(card));
		}
	}
	

	@Override
	public IDTO from(Client client) {
		ClientDTO clientDTO = new ClientDTO();
		this.from(client, clientDTO);
		
		clientDTO.setFirstName(client.getName());
		clientDTO.setLastName(client.getLastName());
		clientDTO.setEmail(client.getUser().getEmail());
		clientDTO.setGender(client.getGender());
		clientDTO.setCpf(client.getCpf());
		clientDTO.setBirthDate(client.getBirthDate());
		clientDTO.setPhone(client.getContact().getDdd() + client.getContact().getNumber());
		clientDTO.setTelephoneType(client.getContact().getTelephoneType());
		clientDTO.setPassword(SystemVariable.descrypt(client.getUser().getPassword()));
		clientDTO.SetDeliveryAddress(client.getAddresses());
		clientDTO.SetCreditCards(client.getCards());
		
		clientDTO.setRoles(new ArrayList<>(client.getUser().getRoles()));
		
		return clientDTO;
	}

	@Override
	public Client fill(Long... params) {
		Client client = new Client();
		User user = new User();
		Contact contact = new Contact();
		
		client.setUser(user);
		client.setContact(contact);
		
		client.setName(firstName);
		client.setLastName(lastName);
		client.getContact().setEmail(email);
		client.getUser().setEmail(email);
		client.setGender(gender);
		client.setCpf(cpf);
		client.setBirthDate(birthDate);
		client.getContact().setDdd((phone != null) ? phone.substring(0, 2):null);
		client.getContact().setNumber((phone != null) ? phone.substring(2) : null);
		client.getContact().setTelephoneType(telephoneType);
		client.getUser().setPassword(password);
		client.setId((params.length == 0) ? null : params[0]);
		client.setStatus((null == this.status) ? null : this.status);
		
		return client;
	}
}
