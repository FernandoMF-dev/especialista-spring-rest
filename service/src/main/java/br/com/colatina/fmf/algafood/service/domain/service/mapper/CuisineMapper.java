package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuisineMapper extends EntityMapper<CuisineDto, Cuisine> {
}
