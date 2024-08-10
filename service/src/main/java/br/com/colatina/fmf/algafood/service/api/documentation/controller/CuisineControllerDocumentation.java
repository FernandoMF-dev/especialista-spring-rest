package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.api.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = SpringFoxControllerTags.CUISINES)
public interface CuisineControllerDocumentation {
	@ApiOperation("Find a list of all available cuisines")
	ResponseEntity<List<CuisineDto>> findAll();

	@ApiOperation("Find a list of all available cuisines")
	ResponseEntity<CuisinesXmlWrapper> findAllXml();

	@ApiOperation("Find a cuisine by its ID")
	@ApiResponse(responseCode = "400", description = "Invalid Cuisine ID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Cuisine not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<CuisineDto> findById(@ApiParam(value = "ID of a available cuisine", example = "1", required = true) Long id);

	@ApiOperation("Find the first available cuisine it can")
	@ApiResponse(responseCode = "404", description = "No cuisine found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<CuisineDto> findFirst();

	@ApiOperation("Insert a new cuisine")
	@ApiResponse(responseCode = "201", description = "Cuisine created", content = @Content(schema = @Schema(implementation = CuisineDto.class)))
	ResponseEntity<CuisineDto> insert(@ApiParam(name = "body", value = "Cuisine data to create", required = true) CuisineDto dto);

	@ApiOperation("Update a cuisine by its ID")
	@ApiResponse(responseCode = "200", description = "Cuisine updated", content = @Content(schema = @Schema(implementation = CuisineDto.class)))
	@ApiResponse(responseCode = "404", description = "Cuisine not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<CuisineDto> update(@ApiParam(value = "ID of a available cuisine to update", example = "1", required = true) Long id,
									  @ApiParam(name = "body", value = "Cuisine data to update", required = true) CuisineDto dto);

	@ApiOperation("Delete a cuisine by its ID")
	@ApiResponse(responseCode = "204", description = "Cuisine deleted")
	@ApiResponse(responseCode = "404", description = "Cuisine not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of a available cuisine to delete", example = "1", required = true) Long id);
}
