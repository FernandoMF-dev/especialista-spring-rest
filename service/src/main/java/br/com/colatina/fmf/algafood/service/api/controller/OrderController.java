package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.OrderCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping()
	public ResponseEntity<OrderDto> insert(@Valid @RequestBody OrderInsertDto dto) {
		log.debug("REST request to insert a new order: {}", dto);
		return new ResponseEntity<>(orderCrudService.insert(dto), HttpStatus.CREATED);
	}
}
