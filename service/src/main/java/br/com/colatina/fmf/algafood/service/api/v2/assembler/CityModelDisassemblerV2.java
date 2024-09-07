package br.com.colatina.fmf.algafood.service.api.v2.assembler;

import br.com.colatina.fmf.algafood.service.api.v2.model.input.CityInputV2;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityModelDisassemblerV2 {
	private final ModelMapper modelMapper;

	public CityDto toDomainObject(CityInputV2 input) {
		return modelMapper.map(input, CityDto.class);
	}

	public void copyToDomainObject(CityInputV2 input, CityDto dto) {
		dto.setState(new StateDto());

		modelMapper.map(input, dto);
	}
}
