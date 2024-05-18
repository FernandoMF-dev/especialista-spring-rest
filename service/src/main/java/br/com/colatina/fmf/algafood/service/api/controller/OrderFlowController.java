package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.OrderFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders/{orderId}")
public class OrderFlowController {
	private final OrderFlowService orderFlowService;

	@PutMapping("/confirm")
	public ResponseEntity<Void> confirm(@PathVariable Long orderId) {
		log.debug("REST request to set order {} as confirmed", orderId);
		orderFlowService.confirm(orderId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/deliver")
	public ResponseEntity<Void> deliver(@PathVariable Long orderId) {
		log.debug("REST request to set order {} as delivered", orderId);
		orderFlowService.deliver(orderId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/cancel")
	public ResponseEntity<Void> cancel(@PathVariable Long orderId) {
		log.debug("REST request to set order {} as cancelled", orderId);
		orderFlowService.cancel(orderId);
		return ResponseEntity.noContent().build();
	}
}
