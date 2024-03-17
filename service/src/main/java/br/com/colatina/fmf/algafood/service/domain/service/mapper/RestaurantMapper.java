package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {KitchenMapper.class, PaymentMethodMapper.class, AddressMapper.class, ProductMapper.class})
public interface RestaurantMapper extends EntityMapper<RestaurantDto, Restaurant> {

	@Mapping(source = "kitchen.id", target = "kitchenId")
	@Mapping(source = "kitchen.name", target = "kitchenName")
	RestaurantListDto toListDto(Restaurant entity);

}
