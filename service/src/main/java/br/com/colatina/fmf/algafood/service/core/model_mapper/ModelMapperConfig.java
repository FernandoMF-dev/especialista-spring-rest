package br.com.colatina.fmf.algafood.service.core.model_mapper;

import br.com.colatina.fmf.algafood.service.api.v2.model.input.CityInputV2;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(CityInputV2.class, CityDto.class)
				.addMappings(mapper -> mapper.skip(CityDto::setId));

		return modelMapper;
	}

}
