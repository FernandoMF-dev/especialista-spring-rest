package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.State;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDto, State> {
}
