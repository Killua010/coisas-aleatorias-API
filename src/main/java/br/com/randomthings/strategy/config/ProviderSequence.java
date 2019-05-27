package br.com.randomthings.strategy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.randomthings.domain.Category;
import br.com.randomthings.domain.DomainEntity;
import br.com.randomthings.domain.Provider;
import br.com.randomthings.strategy.Sequence;
import br.com.randomthings.strategy.provider.StProviderRegisterHelp;
import br.com.randomthings.strategy.standard.StLastUpdate;
import br.com.randomthings.strategy.standard.StRegistration;

@Configuration
public class ProviderSequence {

	@Autowired
	StProviderRegisterHelp stProviderRegisterHelp;
	
	@Autowired
	StRegistration stRegistration;
	
	@Autowired
	StLastUpdate stLastUpdate;
	
	@Bean("SAVE_PROVIDER")
	public Sequence<Provider> saveCategory() {
		return new Sequence<Provider>()
				.add(stProviderRegisterHelp)
				.add(stRegistration);
	}
	
	@Bean("UPDATE_PROVIDER")
	public Sequence<Provider> updateCategory() {
		System.err.println("oi");
		return new Sequence<Provider>()
				.add(stProviderRegisterHelp)
				.add(stLastUpdate);
	}

}
