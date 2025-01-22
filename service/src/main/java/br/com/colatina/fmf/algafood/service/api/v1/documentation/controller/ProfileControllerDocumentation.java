package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.PROFILES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface ProfileControllerDocumentation {
	@Operation(summary = "Find a list of all profiles")
	CollectionModel<ProfileDto> findAll();

	@Operation(summary = "Find a profile by its ID")
	ResponseEntity<ProfileDto> findById(@Parameter(description = "ID of a profile", example = "1", required = true) Long id);

	@Operation(summary = "Insert a new profile")
	@ApiResponse(responseCode = "201")
	ResponseEntity<ProfileDto> insert(@RequestBody(description = "Profile data to create", required = true) ProfileDto dto);

	@Operation(summary = "Update a profile by its ID")
	ResponseEntity<ProfileDto> update(@Parameter(description = "ID of a profile to update", example = "1", required = true) Long id,
									  @RequestBody(description = "Profile data to update", required = true) ProfileDto dto);

	@Operation(summary = "Delete a profile by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of a profile to delete", example = "1", required = true) Long id);
}
