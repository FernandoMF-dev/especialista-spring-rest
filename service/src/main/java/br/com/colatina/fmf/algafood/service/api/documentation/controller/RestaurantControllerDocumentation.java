package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = SpringFoxControllerTags.RESTAURANTS)
public interface RestaurantControllerDocumentation {

	@ApiOperation(value = "Find a list of all available restaurants")
	@ApiResponse(responseCode = "200", description = "List of restaurants retrieved")
	CollectionModel<RestaurantListDto> findAll();

	@ApiOperation(value = "Filter restaurants by freight fee")
	@ApiResponse(responseCode = "200", description = "List of restaurants retrieved")
	CollectionModel<RestaurantListDto> filterByFreightFee(@ApiParam(value = "Name of the restaurant", example = "Burger King") String name,
																		  @ApiParam(value = "Minimum freight fee", example = "5.00") Double min,
																		  @ApiParam(value = "Maximum freight fee", example = "15.00") Double max);

	@ApiOperation(value = "Find a paginated list of restaurants with filters")
	@ApiResponse(responseCode = "200", description = "Paginated list of restaurants retrieved")
	PagedModel<RestaurantListDto> page(RestaurantPageFilter filter, Pageable pageable);

	@ApiOperation(value = "Find the first available restaurant")
	@ApiResponse(responseCode = "200", description = "First available restaurant retrieved")
	ResponseEntity<RestaurantDto> findFirst();

	@ApiOperation(value = "Find a restaurant by its ID")
	@ApiResponse(responseCode = "200", description = "Restaurant retrieved")
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<RestaurantDto> findById(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long id);

	@ApiOperation(value = "Insert a new restaurant")
	@ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content(schema = @Schema(implementation = RestaurantDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<RestaurantDto> insert(@ApiParam(name = "body", value = "Restaurant data to create", required = true) RestaurantFormDto dto);

	@ApiOperation(value = "Update an existing restaurant")
	@ApiResponse(responseCode = "200", description = "Restaurant updated", content = @Content(schema = @Schema(implementation = RestaurantDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<RestaurantDto> update(@ApiParam(name = "body", value = "Restaurant data to update", required = true) RestaurantFormDto dto,
										 @ApiParam(value = "ID of the restaurant", example = "1", required = true) Long id);

	@ApiOperation(value = "Toggle the active status of a restaurant")
	@ApiResponse(responseCode = "204", description = "Restaurant active status toggled")
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> toggleActive(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long id,
									  @ApiParam(value = "Active status value", example = "true", required = true) Boolean active);

	@ApiOperation(value = "Toggle the active status of multiple restaurants")
	@ApiResponse(responseCode = "204", description = "Restaurants active status toggled")
	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> toggleAllActive(@ApiParam(value = "Active status value", example = "true", required = true) Boolean active,
										 @ApiParam(name = "body", value = "List of restaurant IDs", required = true) List<Long> restaurantIds);

	@ApiOperation(value = "Toggle the open status of a restaurant")
	@ApiResponse(responseCode = "204", description = "Restaurant open status toggled")
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> toggleOpen(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long id,
									@ApiParam(value = "Open status value", example = "true", required = true) Boolean open);

	@ApiOperation(value = "Delete a restaurant by its ID")
	@ApiResponse(responseCode = "204", description = "Restaurant deleted")
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long id);
}
