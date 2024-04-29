package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface RestaurantFormMapper extends EntityMapper<RestaurantFormDto, Restaurant> {
	@Override
	@Mapping(source = "cuisineId", target = "cuisine.id")
	@Mapping(target = "paymentMethods", ignore = true)
	Restaurant toEntity(RestaurantFormDto dto);

	@Override
	@Mapping(source = "cuisine.id", target = "cuisineId")
	@Mapping(target = "paymentMethods", ignore = true)
	RestaurantFormDto toDto(Restaurant entity);

	@AfterMapping
	default void mapPaymentMethodsToEntity(RestaurantFormDto dto, @MappingTarget Restaurant entity) {
		List<PaymentMethod> paymentMethods = dto.getPaymentMethods().stream()
				.map(PaymentMethod::new)
				.collect(Collectors.toList());

		entity.setPaymentMethods(paymentMethods);
	}

	@AfterMapping
	default void mapPaymentMethodsToDto(@MappingTarget RestaurantFormDto dto, Restaurant entity) {
		List<Long> paymentMethodIds = entity.getPaymentMethods().stream()
				.map(PaymentMethod::getId)
				.collect(Collectors.toList());

		dto.setPaymentMethods(paymentMethodIds);
	}
}
