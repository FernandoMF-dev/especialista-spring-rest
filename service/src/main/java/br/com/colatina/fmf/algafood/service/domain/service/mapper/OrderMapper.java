package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RestaurantMapper.class, PaymentMethodMapper.class, OrderProductMapper.class, AddressMapper.class})
public interface OrderMapper extends EntityMapper<OrderDto, Order> {
}
