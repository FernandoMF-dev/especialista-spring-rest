package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderProductMapper extends EntityMapper<OrderProductDto, OrderProduct> {
	@Override
	@Mapping(source = "orderId", target = "order.id")
	@Mapping(source = "productId", target = "product.id")
	OrderProduct toEntity(OrderProductDto dto);

	@Override
	@Mapping(source = "order.id", target = "orderId")
	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "product.name", target = "productName")
	@Mapping(source = "product.description", target = "productDescription")
	OrderProductDto toDto(OrderProduct entity);
}
