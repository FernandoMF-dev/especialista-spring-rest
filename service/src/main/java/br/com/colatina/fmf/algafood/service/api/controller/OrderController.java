package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.core.pageable.PageableTranslator;
import br.com.colatina.fmf.algafood.service.domain.service.OrderCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.OrderPageFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderCrudService orderCrudService;

	@GetMapping()
	public ResponseEntity<List<OrderListDto>> findAll() {
		log.debug("REST request to find all orders");
		List<OrderListDto> orders = orderCrudService.findAll();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<OrderDto> findById(@PathVariable String uuid) {
		log.debug("REST request to find the order with UUID code {}", uuid);
		OrderDto order = orderCrudService.findDtoByUuid(uuid);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<OrderListDto> page(OrderPageFilter filter, Pageable pageable) {
		log.debug("REST request to perform a paged search of orders with filters {} and with the page configuration {}", filter, pageable);
		pageable = PageableTranslator.translate(pageable, OrderListDto.class);
		return orderCrudService.page(filter, pageable);
	}

	@PostMapping()
	public ResponseEntity<OrderDto> insert(@Valid @RequestBody OrderInsertDto dto) {
		log.debug("REST request to insert a new order: {}", dto);
		return new ResponseEntity<>(orderCrudService.insert(dto), HttpStatus.CREATED);
	}
}
