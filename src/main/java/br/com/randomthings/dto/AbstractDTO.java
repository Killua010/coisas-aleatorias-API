package br.com.randomthings.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.randomthings.domain.DomainEntity;
import lombok.Data;

@Data
public abstract class AbstractDTO<Domain extends DomainEntity> implements IDTO<Domain>{
	protected Long id;

	protected Boolean status;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime creationDate;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime lastUpdate;

	protected void from(Domain domain, AbstractDTO dto) {	
		dto.setId(domain.getId());
		dto.setStatus(domain.getStatus());
		dto.setLastUpdate(domain.getLastUpdate());
		dto.setCreationDate(domain.getCreationDate());
	}
		
}
