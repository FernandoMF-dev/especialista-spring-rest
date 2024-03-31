package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
import br.com.colatina.fmf.algafood.service.domain.service.OrderCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderCrudService orderCrudService;

	@PostMapping()
	public ResponseEntity<OrderDto> insert(@Valid @RequestBody OrderInsertDto dto) {
		log.debug("REST request to insert a new order: {}", dto);

		try {
			return new ResponseEntity<>(orderCrudService.insert(dto), HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
