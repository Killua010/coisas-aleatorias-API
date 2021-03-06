package br.com.randomthings.services.provider.web;

import java.util.List;

import br.com.randomthings.dto.ProviderDTO;

public interface ProviderServiceWeb {
	ProviderDTO findByName(String name);
	
	List<ProviderDTO> findByCategory(Long id);
}
