package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.CuisineCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.v1.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
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

@Tag(name = SpringDocControllerTags.CUISINES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface CuisineControllerDocumentation {
	@Operation(summary = "Find a list of all available cuisines")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CuisineCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<CuisineDto>> findAll();

	@Operation(summary = "Find a list of all available cuisines")
	ResponseEntity<CuisinesXmlWrapper> findAllXml();

	@Operation(summary = "Find a cuisine by its ID")
	ResponseEntity<CuisineDto> findById(@Parameter(description = "ID of a available cuisine", example = "1", required = true) Long id);

	@Operation(summary = "Find the first available cuisine it can")
	ResponseEntity<CuisineDto> findFirst();

	@Operation(summary = "Insert a new cuisine")
	@ApiResponse(responseCode = "201")
	ResponseEntity<CuisineDto> insert(@RequestBody(description = "Cuisine data to create", required = true) CuisineDto dto);

	@Operation(summary = "Update a cuisine by its ID")
	ResponseEntity<CuisineDto> update(@Parameter(description = "ID of a available cuisine to update", example = "1", required = true) Long id,
									  @RequestBody(description = "Cuisine data to update", required = true) CuisineDto dto);

	@Operation(summary = "Delete a cuisine by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of a available cuisine to delete", example = "1", required = true) Long id);
}
