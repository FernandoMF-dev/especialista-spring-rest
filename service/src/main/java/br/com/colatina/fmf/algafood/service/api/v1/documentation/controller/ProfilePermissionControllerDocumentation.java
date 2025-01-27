package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.PermissionCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.PROFILES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface ProfilePermissionControllerDocumentation {
	@Operation(summary = "Find all permissions associated with a profile")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PermissionCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<PermissionDto>> findAll(@Parameter(description = "ID of the profile", example = "1", required = true) Long profileId);

	@Operation(summary = "Associate a permission with a profile")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> associate(@Parameter(description = "ID of the profile", example = "1", required = true) Long profileId,
								   @Parameter(description = "ID of the permission", example = "1", required = true) Long permissionId);

	@Operation(summary = "Disassociate a permission from a profile")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> disassociate(@Parameter(description = "ID of the profile", example = "1", required = true) Long profileId,
									  @Parameter(description = "ID of the permission", example = "1", required = true) Long permissionId);
}
