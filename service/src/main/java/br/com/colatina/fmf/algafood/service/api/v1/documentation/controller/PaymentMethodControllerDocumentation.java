package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.PaymentMethodCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(name = SpringDocControllerTags.PAYMENT_METHODS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface PaymentMethodControllerDocumentation {
	@Operation(summary = "Find a list of all available payment methods")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PaymentMethodCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<PaymentMethodDto>> findAll(ServletWebRequest request);

	@Operation(summary = "Find a payment method by its ID")
	ResponseEntity<PaymentMethodDto> findById(@Parameter(description = "ID of a payment method", example = "1", required = true) Long id,
											  @Parameter(hidden = true) ServletWebRequest request);

	@Operation(summary = "Insert a new payment method")
	@ApiResponse(responseCode = "201")
	ResponseEntity<PaymentMethodDto> insert(@RequestBody(description = "Payment method data to create", required = true) PaymentMethodDto dto);

	@Operation(summary = "Update a payment method by its ID")
	ResponseEntity<PaymentMethodDto> update(@Parameter(description = "ID of a payment method to update", example = "1", required = true) Long id,
											@RequestBody(description = "Payment method data to update", required = true) PaymentMethodDto dto);

	@Operation(summary = "Delete a payment method by its ID")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(@Parameter(description = "ID of a payment method to delete", example = "1", required = true) Long id);
}
