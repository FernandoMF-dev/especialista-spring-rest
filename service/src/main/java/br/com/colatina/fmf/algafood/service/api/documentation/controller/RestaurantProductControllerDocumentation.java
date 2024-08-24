package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.PRODUCTS)
public interface RestaurantProductControllerDocumentation {
	@ApiOperation("Find all products for a restaurant")
	@ApiResponse(responseCode = "200", description = "List of products retrieved")
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<CollectionModel<ProductDto>> findAll(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId);

	@ApiOperation("Find a product by its ID for a restaurant")
	@ApiResponse(responseCode = "200", description = "Product retrieved")
	@ApiResponse(responseCode = "404", description = "Restaurant or product not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProductDto> findById(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
										@ApiParam(value = "ID of the product", example = "1", required = true) Long productId);

	@ApiOperation("Insert a new product for a restaurant")
	@ApiResponse(responseCode = "201", description = "Product created", content = @Content(schema = @Schema(implementation = ProductDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProductDto> insert(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									  @ApiParam(name = "body", value = "Product data to create", required = true) ProductDto dto);

	@ApiOperation("Update an existing product for a restaurant")
	@ApiResponse(responseCode = "200", description = "Product updated", content = @Content(schema = @Schema(implementation = ProductDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Restaurant or product not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProductDto> update(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									  @ApiParam(value = "ID of the product", example = "1", required = true) Long productId,
									  @ApiParam(name = "body", value = "Product data to update", required = true) ProductDto dto);

	@ApiOperation("Delete a product by its ID for a restaurant")
	@ApiResponse(responseCode = "204", description = "Product deleted")
	@ApiResponse(responseCode = "404", description = "Restaurant or product not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
								@ApiParam(value = "ID of the product", example = "1", required = true) Long productId);
}
