package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantProductControllerDocumentation {
    CollectionModel<ProductDto> findAll(Long restaurantId);

    ResponseEntity<ProductDto> findById(Long restaurantId, Long productId);

    ResponseEntity<ProductDto> insert(Long restaurantId, ProductDto dto);

    ResponseEntity<ProductDto> update(Long restaurantId, Long productId, ProductDto dto);

    ResponseEntity<Void> delete(Long restaurantId, Long productId);
}
