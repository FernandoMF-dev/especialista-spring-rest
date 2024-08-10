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
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = SpringFoxControllerTags.PROFILES)
public interface ProfileControllerDocumentation {
	@ApiOperation("Find a list of all profiles")
	ResponseEntity<List<ProfileDto>> findAll();

	@ApiOperation("Find a profile by its ID")
	@ApiResponse(responseCode = "400", description = "Invalid Profile ID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProfileDto> findById(@ApiParam(value = "ID of a profile", example = "1", required = true) Long id);

	@ApiOperation("Insert a new profile")
	@ApiResponse(responseCode = "201", description = "Profile created", content = @Content(schema = @Schema(implementation = ProfileDto.class)))
	ResponseEntity<ProfileDto> insert(@ApiParam(name = "body", value = "Profile data to create", required = true) ProfileDto dto);

	@ApiOperation("Update a profile by its ID")
	@ApiResponse(responseCode = "200", description = "Profile updated", content = @Content(schema = @Schema(implementation = ProfileDto.class)))
	@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProfileDto> update(@ApiParam(value = "ID of a profile to update", example = "1", required = true) Long id,
									  @ApiParam(name = "body", value = "Profile data to update", required = true) ProfileDto dto);

	@ApiOperation("Delete a profile by its ID")
	@ApiResponse(responseCode = "204", description = "Profile deleted")
	@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of a profile to delete", example = "1", required = true) Long id);
}
