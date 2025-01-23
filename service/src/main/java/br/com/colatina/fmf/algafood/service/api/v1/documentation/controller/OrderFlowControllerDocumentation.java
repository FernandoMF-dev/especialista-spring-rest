package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.ORDERS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface OrderFlowControllerDocumentation {
	@Operation(summary = "Confirm an order by its UUID")
	@ApiResponse(responseCode = "204")
	@ApiResponse(responseCode = "409", description = "Order cannot be confirmed because of its current status", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> confirm(@Parameter(description = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String orderUuid);

	@Operation(summary = "Deliver an order by its UUID")
	@ApiResponse(responseCode = "204")
	@ApiResponse(responseCode = "409", description = "Order cannot be delivered because of its current status", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> deliver(@Parameter(description = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String orderUuid);

	@Operation(summary = "Cancel an order by its UUID")
	@ApiResponse(responseCode = "204")
	@ApiResponse(responseCode = "409", description = "Order cannot be cancelled because of its current status", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> cancel(@Parameter(description = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String orderUuid);
}
