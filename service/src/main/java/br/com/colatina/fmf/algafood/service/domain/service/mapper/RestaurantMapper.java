package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDto, Restaurant> {

	@Override
	@Mapping(source = "kitchenId", target = "kitchen.id")
	Restaurant toEntity(RestaurantDto dto);

	@Override
	@Mapping(source = "kitchen.id", target = "kitchenId")
	RestaurantDto toDto(Restaurant entity);

	@AfterMapping
	default void mapKitchen(@MappingTarget RestaurantDto dto, Restaurant entity) {
		if (Objects.isNull(dto.getKitchenId())) {
			entity.setKitchen(null);
		}
	}
}
