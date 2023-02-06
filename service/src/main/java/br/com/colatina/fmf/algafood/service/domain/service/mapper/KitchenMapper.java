package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KitchenMapper extends EntityMapper<KitchenDto, Kitchen> {
}
