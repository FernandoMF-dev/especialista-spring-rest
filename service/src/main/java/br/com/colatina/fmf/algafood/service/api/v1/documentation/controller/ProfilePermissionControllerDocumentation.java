package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.PROFILES)
public interface ProfilePermissionControllerDocumentation {

	@ApiOperation("Find all permissions associated with a profile")
	@ApiResponse(responseCode = "200", description = "Permissions retrieved")
	@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	CollectionModel<PermissionDto> findAll(@ApiParam(value = "ID of the profile", example = "1", required = true) Long profileId);

	@ApiOperation("Associate a permission with a profile")
	@ApiResponse(responseCode = "204", description = "Permission associated")
	@ApiResponse(responseCode = "404", description = "Profile or permission not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> associate(@ApiParam(value = "ID of the profile", example = "1", required = true) Long profileId,
								   @ApiParam(value = "ID of the permission", example = "1", required = true) Long permissionId);

	@ApiOperation("Disassociate a permission from a profile")
	@ApiResponse(responseCode = "204", description = "Permission disassociated")
	@ApiResponse(responseCode = "404", description = "Profile or permission not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> disassociate(@ApiParam(value = "ID of the profile", example = "1", required = true) Long profileId,
									  @ApiParam(value = "ID of the permission", example = "1", required = true) Long permissionId);
}
