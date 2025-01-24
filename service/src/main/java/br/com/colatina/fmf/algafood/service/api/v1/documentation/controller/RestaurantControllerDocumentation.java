package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.core.openapi.annotations.PageableParameterDocs;
import br.com.colatina.fmf.algafood.service.core.openapi.annotations.RestaurantPageFilterParameterDocs;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = SpringDocControllerTags.RESTAURANTS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantControllerDocumentation {

	@Operation(summary = "Find a list of all available restaurants")
	CollectionModel<RestaurantListDto> findAll();

	@Operation(summary = "Filter restaurants by freight fee")
	CollectionModel<RestaurantListDto> filterByFreightFee(@Parameter(description = "Name of the restaurant", example = "Burger King") String name,
														  @Parameter(description = "Minimum freight fee", example = "5.00") Double min,
														  @Parameter(description = "Maximum freight fee", example = "15.00") Double max);

	@PageableParameterDocs
	@RestaurantPageFilterParameterDocs
	PagedModel<RestaurantListDto> page(@Parameter(hidden = true) RestaurantPageFilter filter,
									   @Parameter(hidden = true) Pageable pageable);

	@Operation(summary = "Find the first available restaurant")
	ResponseEntity<RestaurantDto> findFirst();

	@Operation(summary = "Find a restaurant by its ID")
	ResponseEntity<RestaurantDto> findById(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long id);

	@Operation(summary = "Insert a new restaurant")
	@ApiResponse(responseCode = "201")
	ResponseEntity<RestaurantDto> insert(@RequestBody(description = "Restaurant data to create", required = true) RestaurantFormDto dto);

	@Operation(summary = "Update an existing restaurant")
	ResponseEntity<RestaurantDto> update(@RequestBody(description = "Restaurant data to update", required = true) RestaurantFormDto dto,
										 @Parameter(description = "ID of the restaurant", example = "1", required = true) Long id);

	@Operation(summary = "Toggle the active status of a restaurant")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> toggleActive(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long id,
									  @Parameter(description = "Active status value", example = "true", required = true) Boolean active);

	@Operation(summary = "Toggle the active status of multiple restaurants")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> toggleAllActive(@Parameter(description = "Active status value", example = "true", required = true) Boolean active,
										 @RequestBody(description = "List of restaurant IDs", required = true) List<Long> restaurantIds);

	@Operation(summary = "Toggle the open status of a restaurant")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> toggleOpen(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long id,
									@Parameter(description = "Open status value", example = "true", required = true) Boolean open);

	@Operation(summary = "Delete a restaurant by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long id);
}
