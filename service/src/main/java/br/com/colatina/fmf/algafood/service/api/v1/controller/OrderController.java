package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.OrderControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.OrderHateoas;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.OrderListHateoas;
import br.com.colatina.fmf.algafood.service.core.pageable.PageableTranslator;
import br.com.colatina.fmf.algafood.service.core.security.AppSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.OrderCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.OrderPageFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerDocumentation {
	private final OrderCrudService orderCrudService;
	private final OrderHateoas orderHateoas;
	private final OrderListHateoas orderListHateoas;

	private final AppSecurity appSecurity;

	@Override
	@GetMapping()
	public CollectionModel<OrderListDto> findAll() {
		log.debug("REST request to find all orders");
		List<OrderListDto> orders = orderCrudService.findAll();
		return orderListHateoas.mapCollectionModel(orders);
	}

	@Override
	@GetMapping("/{uuid}")
	public ResponseEntity<OrderDto> findByUuid(@PathVariable String uuid) {
		log.debug("REST request to find the order with UUID code {}", uuid);
		OrderDto order = orderCrudService.findDtoByUuid(uuid);
		return new ResponseEntity<>(orderHateoas.mapModel(order), HttpStatus.OK);
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public PagedModel<OrderListDto> page(OrderPageFilter filter, Pageable pageable) {
		log.debug("REST request to perform a paged search of orders with filters {} and with the page configuration {}", filter, pageable);
		pageable = PageableTranslator.translate(pageable, OrderListDto.class);
		Page<OrderListDto> page = orderCrudService.page(filter, pageable);
		return orderListHateoas.mapPagedModel(page);
	}

	@Override
	@PostMapping()
	public ResponseEntity<OrderDto> insert(@Valid @RequestBody OrderInsertDto dto) {
		log.debug("REST request to insert a new order: {}", dto);
		dto.setCustomerId(appSecurity.getUserId());
		OrderDto order = orderCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(order.getCode());
		return new ResponseEntity<>(orderHateoas.mapModel(order), HttpStatus.CREATED);
	}
}
