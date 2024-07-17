package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.PaymentMethodCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {
	private final PaymentMethodCrudService paymentMethodCrudService;

	@GetMapping()
	public ResponseEntity<List<PaymentMethodDto>> findAll() {
		log.debug("REST request to find all payment methods");
		List<PaymentMethodDto> result = paymentMethodCrudService.findAll();
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
				.body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentMethodDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the payment method with ID: {}", id);
		PaymentMethodDto result = paymentMethodCrudService.findDtoById(id);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
				.body(result);
	}

	@PostMapping()
	public ResponseEntity<PaymentMethodDto> insert(@Valid @RequestBody PaymentMethodDto dto) {
		log.debug("REST request to insert a new payment method: {}", dto);
		return new ResponseEntity<>(paymentMethodCrudService.insert(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaymentMethodDto> update(@Valid @RequestBody PaymentMethodDto dto, @PathVariable Long id) {
		log.debug("REST request to update payment method with id {}: {}", id, dto);
		return new ResponseEntity<>(paymentMethodCrudService.update(dto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete payment method with id {}", id);
		paymentMethodCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
