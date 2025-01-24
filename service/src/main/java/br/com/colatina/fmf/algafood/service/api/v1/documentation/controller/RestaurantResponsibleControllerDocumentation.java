package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.RESTAURANTS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantResponsibleControllerDocumentation {
	@Operation(summary = "Find all users responsible for a restaurant")
	CollectionModel<AppUserDto> findAll(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId);

	@Operation(summary = "Associate a user as a responsible for a restaurant")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> associate(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
								   @Parameter(description = "ID of the user to be associated as a responsible for the restaurant", example = "1", required = true) Long responsibleId);

	@Operation(summary = "Disassociate a user from being a responsible for a restaurant")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> disassociate(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									  @Parameter(description = "ID of the user to be disassociated from being a responsible for the restaurant", example = "1", required = true) Long responsibleId);
}
