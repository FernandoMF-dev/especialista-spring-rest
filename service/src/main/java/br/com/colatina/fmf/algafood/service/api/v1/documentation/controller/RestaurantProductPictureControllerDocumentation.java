package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Tag(name = SpringDocControllerTags.PRODUCTS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantProductPictureControllerDocumentation {
	@Operation(summary = "Find the picture of a product from a restaurant")
	@ApiResponse(responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProductPictureDto.class)),
			@Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
			@Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
	})
	@ApiResponse(responseCode = "302", description = "Picture saved in external storage (only when returning a binary)")
	ResponseEntity<ProductPictureDto> findPicture(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
												  @Parameter(description = "ID of the product", example = "1", required = true) Long productId);

	@Operation(summary = "Find the picture of a product from a restaurant", hidden = true)
	ResponseEntity<InputStreamResource> getPictureFile(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
													   @Parameter(description = "ID of the product", example = "1", required = true) Long productId,
													   @Parameter(hidden = true) String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	@Operation(summary = "Update the picture of a product from a restaurant")
	ResponseEntity<ProductPictureDto> updatePicture(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
													@Parameter(description = "ID of the product", example = "1", required = true) Long productId,
													@RequestBody(description = "Picture data to update", required = true) ProductPictureInsertDto picture) throws IOException;

	@Operation(summary = "Delete the picture of a product from a restaurant")
	@ApiResponse(responseCode = "204")
	@ApiResponse(responseCode = "404")
	ResponseEntity<Void> deletePicture(@Parameter(description = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									   @Parameter(description = "ID of the product", example = "1", required = true) Long productId);
}
