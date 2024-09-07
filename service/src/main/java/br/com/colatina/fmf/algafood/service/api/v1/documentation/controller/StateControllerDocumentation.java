package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.STATES)
public interface StateControllerDocumentation {
	@ApiOperation("Find a list of all available states")
	CollectionModel<StateDto> findAll();

	@ApiOperation("Find a state by its ID")
	@ApiResponse(responseCode = "400", description = "Invalid state ID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "State not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<StateDto> findById(@ApiParam(value = "ID of a state", example = "1", required = true) Long id);

	@ApiOperation("Insert a new state")
	@ApiResponse(responseCode = "201", description = "State created", content = @Content(schema = @Schema(implementation = StateDto.class)))
	ResponseEntity<StateDto> insert(@ApiParam(name = "body", value = "State data to create", required = true) StateDto dto);

	@ApiOperation("Update a state by its ID")
	@ApiResponse(responseCode = "200", description = "State updated", content = @Content(schema = @Schema(implementation = StateDto.class)))
	@ApiResponse(responseCode = "404", description = "State not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<StateDto> update(@ApiParam(value = "ID of a state to update", example = "1", required = true) Long id,
									@ApiParam(name = "body", value = "State data to update", required = true) StateDto dto);

	@ApiOperation("Delete a state by its ID")
	@ApiResponse(responseCode = "204", description = "State deleted")
	@ApiResponse(responseCode = "404", description = "State not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of a state to delete", example = "1", required = true) Long id);
}
