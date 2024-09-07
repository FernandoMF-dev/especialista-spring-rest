package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Api(tags = SpringFoxControllerTags.PRODUCTS)
public interface RestaurantProductPictureControllerDocumentation {
	@ApiOperation(value = "Find the picture of a product from a restaurant", produces = "application/json, image/jpeg, image/png")
	@ApiResponse(responseCode = "200", description = "Picture retrieved")
	@ApiResponse(responseCode = "404", description = "Restaurant or product not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProductPictureDto> findPicture(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
												  @ApiParam(value = "ID of the product", example = "1", required = true) Long productId);

	@ApiOperation(value = "Find the picture of a product from a restaurant", hidden = true)
	ResponseEntity<InputStreamResource> getPictureFile(Long restaurantId, Long productId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	@ApiOperation(value = "Update the picture of a product from a restaurant",
			notes = "The \"Try it out\" feature of Swagger UI is bugged for file upload in the documentation page. " +
					"I could implement a workaround, but I decided no to because it seems really prompted to errors in the application. " +
					"So, to test this endpoint, I recommend using a REST client like Insomnia or Postman.")
	@ApiResponse(responseCode = "200", description = "Picture updated", content = @Content(schema = @Schema(implementation = ProductPictureDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Restaurant or product not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<ProductPictureDto> updatePicture(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
													@ApiParam(value = "ID of the product", example = "1", required = true) Long productId,
													@ApiParam(name = "body", value = "Picture data to update", required = true) ProductPictureInsertDto picture) throws IOException;

	@ApiOperation(value = "Delete the picture of a product from a restaurant")
	@ApiResponse(responseCode = "204", description = "Picture deleted")
	@ApiResponse(responseCode = "404", description = "Restaurant or product not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> deletePicture(@ApiParam(value = "ID of the restaurant", example = "1", required = true) Long restaurantId,
									   @ApiParam(value = "ID of the product", example = "1", required = true) Long productId);
}
