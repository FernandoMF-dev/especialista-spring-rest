package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = SpringFoxControllerTags.PAYMENT_METHODS)
public interface PaymentMethodControllerDocumentation {
	@ApiOperation("Find a list of all available payment methods")
	ResponseEntity<CollectionModel<PaymentMethodDto>> findAll(ServletWebRequest request);

	@ApiOperation("Find a payment method by its ID")
	@ApiResponse(responseCode = "400", description = "Invalid payment method ID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Payment method not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<PaymentMethodDto> findById(@ApiParam(value = "ID of a payment method", example = "1", required = true) Long id, ServletWebRequest request);

	@ApiOperation("Insert a new payment method")
	@ApiResponse(responseCode = "201", description = "Payment method created", content = @Content(schema = @Schema(implementation = PaymentMethodDto.class)))
	ResponseEntity<PaymentMethodDto> insert(@ApiParam(name = "body", value = "Payment method data to create", required = true) PaymentMethodDto dto);

	@ApiOperation("Update a payment method by its ID")
	@ApiResponse(responseCode = "200", description = "Payment method updated", content = @Content(schema = @Schema(implementation = PaymentMethodDto.class)))
	@ApiResponse(responseCode = "404", description = "Payment method not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<PaymentMethodDto> update(@ApiParam(value = "ID of a payment method to update", example = "1", required = true) Long id,
											@ApiParam(name = "body", value = "Payment method data to update", required = true) PaymentMethodDto dto);

	@ApiOperation("Delete a payment method by its ID")
	@ApiResponse(responseCode = "204", description = "Payment method deleted")
	@ApiResponse(responseCode = "404", description = "Payment method not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<Void> delete(@ApiParam(value = "ID of a payment method to delete", example = "1", required = true) Long id);
}
