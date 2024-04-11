package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CuisineMapper.class, PaymentMethodMapper.class, AddressMapper.class, ProductMapper.class})
public interface RestaurantMapper extends EntityMapper<RestaurantDto, Restaurant> {

	@Mapping(source = "cuisine.id", target = "cuisineId")
	@Mapping(source = "cuisine.name", target = "cuisineName")
	RestaurantListDto toListDto(Restaurant entity);

}
