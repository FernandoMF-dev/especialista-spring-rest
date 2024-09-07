package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.ORDERS)
public interface OrderFlowControllerDocumentation {
	@ApiOperation("Confirm an order by its UUID")
	@ApiResponse(responseCode = "204", description = "Order confirmed")
	@ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "409", description = "Order cannot be confirmed because of its current status", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> confirm(@ApiParam(value = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String orderUuid);

	@ApiOperation("Deliver an order by its UUID")
	@ApiResponse(responseCode = "204", description = "Order delivered")
	@ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "409", description = "Order cannot be delivered because of its current status", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> deliver(@ApiParam(value = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String orderUuid);

	@ApiOperation("Cancel an order by its UUID")
	@ApiResponse(responseCode = "204", description = "Order cancelled")
	@ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "409", description = "Order cannot be cancelled because of its current status", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> cancel(@ApiParam(value = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String orderUuid);
}
