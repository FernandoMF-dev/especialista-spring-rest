package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Address;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface AddressMapper extends EntityMapper<AddressDto, Address> {
}
