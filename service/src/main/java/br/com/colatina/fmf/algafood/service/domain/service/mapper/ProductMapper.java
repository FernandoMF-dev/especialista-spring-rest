package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
	@Override
	@Mapping(source = "restaurantId", target = "restaurant.id")
	@Mapping(source = "restaurantName", target = "restaurant.name")
	Product toEntity(ProductDto dto);

	@Override
	@InheritInverseConfiguration
	ProductDto toDto(Product entity);
}
