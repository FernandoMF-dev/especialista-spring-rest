package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.ProfileCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.USERS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface AppUserProfileControllerDocumentation {
	@Operation(summary = "Find all profiles associated with a user")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProfileCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<ProfileDto>> findAll(@Parameter(description = "ID of the user", example = "1", required = true) Long userId);

	@Operation(summary = "Associate a profile with a user")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> associate(@Parameter(description = "ID of the user", example = "1", required = true) Long userId,
								   @Parameter(description = "ID of the profile", example = "1", required = true) Long profileId);

	@Operation(summary = "Disassociate a profile from a user")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> disassociate(@Parameter(description = "ID of the user", example = "1", required = true) Long userId,
									  @Parameter(description = "ID of the profile", example = "1", required = true) Long profileId);
}
