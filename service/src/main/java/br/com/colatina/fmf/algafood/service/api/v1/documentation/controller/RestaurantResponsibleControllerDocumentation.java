package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.RESTAURANTS)
public interface RestaurantResponsibleControllerDocumentation {
	@ApiOperation("Find all users responsible for a restaurant")
	@ApiResponse(responseCode = "200", description = "List of users responsible for the restaurant")
	@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	CollectionModel<AppUserDto> findAll(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId);

	@ApiOperation("Associate a user as a responsible for a restaurant")
	@ApiResponse(responseCode = "204", description = "Responsible associated")
	@ApiResponse(responseCode = "404", description = "Restaurant or responsible not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> associate(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
								   @ApiParam(value = "ID of the user to be associated as a responsible for the restaurant", example = "1", required = true) Long responsibleId);

	@ApiOperation("Disassociate a user from being a responsible for a restaurant")
	@ApiResponse(responseCode = "204", description = "Responsible disassociated")
	@ApiResponse(responseCode = "404", description = "Restaurant or responsible not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> disassociate(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									  @ApiParam(value = "ID of the user to be disassociated from being a responsible for the restaurant", example = "1", required = true) Long responsibleId);
}
