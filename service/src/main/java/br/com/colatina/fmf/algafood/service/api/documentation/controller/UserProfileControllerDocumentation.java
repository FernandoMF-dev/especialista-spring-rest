package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.USERS)
public interface UserProfileControllerDocumentation {
	@ApiOperation("Find all profiles associated with a user")
	@ApiResponse(responseCode = "200", description = "Profiles retrieved")
	CollectionModel<ProfileDto> findAll(@ApiParam(value = "ID of the user", example = "1", required = true) Long userId);

	@ApiOperation("Associate a profile with a user")
	@ApiResponse(responseCode = "204", description = "Profile associated")
	@ApiResponse(responseCode = "404", description = "User or profile not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> associate(@ApiParam(value = "ID of the user", example = "1", required = true) Long userId,
								   @ApiParam(value = "ID of the profile", example = "1", required = true) Long profileId);

	@ApiOperation("Disassociate a profile from a user")
	@ApiResponse(responseCode = "204", description = "Profile disassociated")
	@ApiResponse(responseCode = "404", description = "User or profile not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> disassociate(@ApiParam(value = "ID of the user", example = "1", required = true) Long userId,
									  @ApiParam(value = "ID of the profile", example = "1", required = true) Long profileId);
}
