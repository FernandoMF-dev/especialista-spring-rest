package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.AppUserCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
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

@Tag(name = SpringDocControllerTags.USERS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface AppUserControllerDocumentation {
	@Operation(summary = "Find all users")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AppUserCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<AppUserDto>> findAll();

	@Operation(summary = "Find a user by ID")
	ResponseEntity<AppUserDto> findById(@Parameter(description = "ID of the user", example = "1", required = true) Long id);

	@Operation(summary = "Insert a new user")
	@ApiResponse(responseCode = "201")
	ResponseEntity<AppUserDto> insert(@RequestBody(description = "User data to insert", required = true) AppUserInsertDto dto);

	@Operation(summary = "Update a user by ID")
	ResponseEntity<AppUserDto> update(@Parameter(description = "ID of the user", example = "1", required = true) Long id,
									  @RequestBody(description = "User data to update", required = true) AppUserDto dto);

	@Operation(summary = "Change the password of a user by ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> changePassword(@Parameter(description = "ID of the user", example = "1", required = true) Long id,
										@RequestBody(description = "Password change data", required = true) PasswordChangeDto dto);

	@Operation(summary = "Delete a user by ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of the user", example = "1", required = true) Long id);
}
