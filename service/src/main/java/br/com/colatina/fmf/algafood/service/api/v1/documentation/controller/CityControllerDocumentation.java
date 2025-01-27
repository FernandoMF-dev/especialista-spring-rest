package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.CityCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
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

@Tag(name = SpringDocControllerTags.CITIES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface CityControllerDocumentation {
	@Operation(summary = "Find a list of all available cities")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CityCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<CityDto>> findAll();

	@Operation(summary = "Find a city by its ID")
	ResponseEntity<CityDto> findById(@Parameter(description = "ID of a city", example = "1", required = true) Long id);

	@Operation(summary = "Insert a new city")
	@ApiResponse(responseCode = "201")
	ResponseEntity<CityDto> insert(@RequestBody(description = "City data to create", required = true) CityDto dto);

	@Operation(summary = "Update a city by its ID")
	ResponseEntity<CityDto> update(@Parameter(description = "ID of a city to update", example = "1", required = true) Long id,
								   @RequestBody(description = "City data to update", required = true) CityDto dto);

	@Operation(summary = "Delete a city by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of a city to delete", example = "1", required = true) Long id);
}
