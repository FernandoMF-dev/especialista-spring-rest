package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.City;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StateMapper.class})
public interface CityMapper extends EntityMapper<CityDto, City> {
}
