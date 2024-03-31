package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderProductInsertDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderProductInsertMapper extends EntityMapper<OrderProductInsertDto, OrderProduct> {
	@Override
	@Mapping(source = "productId", target = "product.id")
	OrderProduct toEntity(OrderProductInsertDto dto);

	@Override
	@InheritInverseConfiguration
	OrderProductInsertDto toDto(OrderProduct entity);
}
