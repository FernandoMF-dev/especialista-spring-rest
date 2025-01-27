package br.com.colatina.fmf.algafood.service.api.v2.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v2.documentation.model.CityCollectionModelOpenApiV2;
import br.com.colatina.fmf.algafood.service.api.v2.model.CityModelV2;
import br.com.colatina.fmf.algafood.service.api.v2.model.input.CityInputV2;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.CITIES_V2)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface CityControllerV2Documentation {
	@Operation(summary = "Find a list of all available cities")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CityCollectionModelOpenApiV2.class)))
	CollectionModel<CityModelV2> findAll();

	@Operation(summary = "Find a city by its ID")
	ResponseEntity<CityModelV2> findById(@Parameter(description = "ID of a city", example = "1", required = true) Long id);

	@Operation(summary = "Insert a new city")
	@ApiResponse(responseCode = "201")
	ResponseEntity<CityModelV2> insert(@RequestBody(description = "City data to create", required = true) CityInputV2 input);

	@Operation(summary = "Update a city by its ID")
	ResponseEntity<CityModelV2> update(@Parameter(description = "ID of a city to update", example = "1", required = true) Long id,
									   @RequestBody(description = "City data to update", required = true) CityInputV2 input);

	@Operation(summary = "Delete a city by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of a city to delete", example = "1", required = true) Long id);
}
