package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.CITIES)
public interface CityControllerDocumentation {
	@ApiOperation("Find a list of all available cities")
	CollectionModel<CityDto> findAll();

	@ApiOperation("Find a city by its ID")
	@ApiResponse(responseCode = "400", description = "Invalid City ID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "City not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<CityDto> findById(@ApiParam(value = "ID of a city", example = "1", required = true) Long id);

	@ApiOperation("Insert a new city")
	@ApiResponse(responseCode = "201", description = "City created", content = @Content(schema = @Schema(implementation = CityDto.class)))
	ResponseEntity<CityDto> insert(@ApiParam(name = "body", value = "City data to create", required = true) CityDto dto);

	@ApiOperation("Update a city by its ID")
	@ApiResponse(responseCode = "200", description = "City updated", content = @Content(schema = @Schema(implementation = CityDto.class)))
	@ApiResponse(responseCode = "404", description = "City not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<CityDto> update(@ApiParam(value = "ID of a city to update", example = "1", required = true) Long id,
								   @ApiParam(name = "body", value = "City data to update", required = true) CityDto dto);

	@ApiOperation("Delete a city by its ID")
	@ApiResponse(responseCode = "204", description = "City deleted")
	@ApiResponse(responseCode = "404", description = "City not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of a city to delete", example = "1", required = true) Long id);
}
