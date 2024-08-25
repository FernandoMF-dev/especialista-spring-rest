package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.OrderPageFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = SpringFoxControllerTags.ORDERS)
public interface OrderControllerDocumentation {
	@ApiOperation("Find a list of all registered orders")
	ResponseEntity<CollectionModel<OrderListDto>> findAll();

	@ApiOperation("Find an order by its unique identifier code")
	@ApiResponse(responseCode = "400", description = "Invalid order UUID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	ResponseEntity<OrderDto> findByUuid(@ApiParam(value = "UUID of an order", example = "123e4567-e89b-12d3-a456-426614174000", required = true) String uuid);

	@ApiOperation("Find a paginated list of orders with filters")
	PagedModel<OrderListDto> page(OrderPageFilter filter, Pageable pageable);

	@ApiOperation("Emits a new order")
	@ApiResponse(responseCode = "201", description = "Order created", content = @Content(schema = @Schema(implementation = OrderDto.class)))
	ResponseEntity<OrderDto> insert(@ApiParam(name = "body", value = "Order data to create", required = true) OrderInsertDto dto);
}
