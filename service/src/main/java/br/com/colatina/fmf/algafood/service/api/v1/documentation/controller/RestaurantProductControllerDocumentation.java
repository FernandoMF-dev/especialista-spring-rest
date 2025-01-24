package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.PRODUCTS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantProductControllerDocumentation {
	@Operation(summary = "Find all products for a restaurant")
	CollectionModel<ProductDto> findAll(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId);

	@Operation(summary = "Find a product by its ID for a restaurant")
	ResponseEntity<ProductDto> findById(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
										@Parameter(description = "ID of the product", example = "1", required = true) Long productId);

	@Operation(summary = "Insert a new product for a restaurant")
	@ApiResponse(responseCode = "201")
	ResponseEntity<ProductDto> insert(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									  @RequestBody(description = "Product data to create", required = true) ProductDto dto);

	@Operation(summary = "Update an existing product for a restaurant")
	ResponseEntity<ProductDto> update(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									  @Parameter(description = "ID of the product", example = "1", required = true) Long productId,
									  @RequestBody(description = "Product data to update", required = true) ProductDto dto);

	@Operation(summary = "Delete a product by its ID for a restaurant")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
								@Parameter(description = "ID of the product", example = "1", required = true) Long productId);
}
