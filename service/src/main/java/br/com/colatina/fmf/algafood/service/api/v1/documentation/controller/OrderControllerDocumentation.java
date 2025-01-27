package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection.OrderCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.page.OrderPageModelOpenApi;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.core.openapi.annotations.OrderPageFilterParameterDocs;
import br.com.colatina.fmf.algafood.service.core.openapi.annotations.PageableParameterDocs;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.OrderPageFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.ORDERS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface OrderControllerDocumentation {
	@Operation(summary = "Find a list of all registered orders")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OrderCollectionModelOpenApi.class)))
	ResponseEntity<CollectionModel<OrderListDto>> findAll();

	@Operation(summary = "Find an order by its unique identifier code")
	ResponseEntity<OrderDto> findByUuid(@Parameter(description = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String uuid);

	@Operation(summary = "Find a paginated list of orders with filters")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OrderPageModelOpenApi.class)))
	@PageableParameterDocs
	@OrderPageFilterParameterDocs
	ResponseEntity<PagedModel<OrderListDto>> page(@Parameter(hidden = true) OrderPageFilter filter,
								  @Parameter(hidden = true) Pageable pageable);

	@Operation(summary = "Emits a new order")
	@ApiResponse(responseCode = "201")
	ResponseEntity<OrderDto> insert(@RequestBody(description = "Order data to create", required = true) OrderInsertDto dto);
}
