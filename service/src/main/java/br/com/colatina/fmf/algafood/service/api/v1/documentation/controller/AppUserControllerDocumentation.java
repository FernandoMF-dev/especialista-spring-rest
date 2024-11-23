package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.USERS)
public interface AppUserControllerDocumentation {

	@ApiOperation("Find all users")
	@ApiResponse(responseCode = "200", description = "Users retrieved")
	CollectionModel<AppUserDto> findAll();

	@ApiOperation("Find a user by ID")
	@ApiResponse(responseCode = "200", description = "User retrieved")
	@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<AppUserDto> findById(@ApiParam(value = "ID of the user", example = "1", required = true) Long id);

	@ApiOperation("Insert a new user")
	@ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = AppUserDto.class)))
	ResponseEntity<AppUserDto> insert(@ApiParam(name = "body", value = "User data to insert", required = true) AppUserInsertDto dto);

	@ApiOperation("Update a user by ID")
	@ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = AppUserDto.class)))
	@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<AppUserDto> update(@ApiParam(value = "ID of the user", example = "1", required = true) Long id,
									  @ApiParam(name = "body", value = "User data to update", required = true) AppUserDto dto);

	@ApiOperation("Change the password of a user by ID")
	@ApiResponse(responseCode = "204", description = "Password changed")
	@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> changePassword(@ApiParam(value = "ID of the user", example = "1", required = true) Long id,
										@ApiParam(name = "body", value = "Password change data", required = true) PasswordChangeDto dto);

	@ApiOperation("Delete a user by ID")
	@ApiResponse(responseCode = "204", description = "User deleted")
	@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of the user", example = "1", required = true) Long id);
}
