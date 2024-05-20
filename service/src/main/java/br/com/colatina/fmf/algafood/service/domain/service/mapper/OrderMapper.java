package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RestaurantMapper.class, PaymentMethodMapper.class, OrderProductMapper.class, AddressMapper.class})
public interface OrderMapper extends EntityMapper<OrderDto, Order> {
	@Override
	@Mapping(target = "status", ignore = true)
	@Mapping(source = "code", target = "uuidCode")
	Order toEntity(OrderDto dto);

	@Override
	@Mapping(source = "uuidCode", target = "code")
	OrderDto toDto(Order entity);
}
