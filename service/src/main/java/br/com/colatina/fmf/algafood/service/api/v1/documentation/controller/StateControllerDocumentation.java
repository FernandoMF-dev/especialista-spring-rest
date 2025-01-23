package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.STATES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface StateControllerDocumentation {
	@Operation(summary = "Find a list of all available states")
	CollectionModel<StateDto> findAll();

	@Operation(summary = "Find a state by its ID")
	ResponseEntity<StateDto> findById(@Parameter(description = "ID of a state", example = "1", required = true) Long id);

	@Operation(summary = "Insert a new state")
	@ApiResponse(responseCode = "201")
	ResponseEntity<StateDto> insert(@RequestBody(description = "State data to create", required = true) StateDto dto);

	@Operation(summary = "Update a state by its ID")
	ResponseEntity<StateDto> update(@Parameter(description = "ID of a state to update", example = "1", required = true) Long id,
									@RequestBody(description = "State data to update", required = true) StateDto dto);

	@Operation(summary = "Delete a state by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of a state to delete", example = "1", required = true) Long id);
}
