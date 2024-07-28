package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderProductInsertMapper.class, AddressMapper.class})
public interface OrderInsertMapper extends EntityMapper<OrderInsertDto, Order> {
	@Override
	@Mapping(source = "customerId", target = "customer.id")
	@Mapping(source = "restaurantId", target = "restaurant.id")
	@Mapping(source = "paymentMethodId", target = "paymentMethod.id")
	Order toEntity(OrderInsertDto dto);

	@Override
	@InheritInverseConfiguration
	OrderInsertDto toDto(Order entity);
}
