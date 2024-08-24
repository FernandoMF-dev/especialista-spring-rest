package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.PaymentMethodControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.hateoas.PaymentMethodHateoas;
import br.com.colatina.fmf.algafood.service.domain.service.PaymentMethodCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerDocumentation {
	private final PaymentMethodCrudService paymentMethodCrudService;
	private final PaymentMethodHateoas paymentMethodHateoas;

	@Override
	@GetMapping()
	public ResponseEntity<CollectionModel<PaymentMethodDto>> findAll(ServletWebRequest request) {
		log.debug("REST request to find all payment methods");
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		OffsetDateTime lastUpdate = paymentMethodCrudService.findLastUpdate();
		String eTag = getDeepETag(lastUpdate);
		if (request.checkNotModified(eTag)) {
			return null;
		}

		List<PaymentMethodDto> result = paymentMethodCrudService.findAll();
		CollectionModel<PaymentMethodDto> collectionModel = paymentMethodHateoas.mapCollectionModel(result);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
				.eTag(eTag)
				.body(collectionModel);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<PaymentMethodDto> findById(@PathVariable Long id, ServletWebRequest request) {
		log.debug("REST request to find the payment method with ID: {}", id);
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		OffsetDateTime lastUpdate = paymentMethodCrudService.findLastUpdateById(id);
		String eTag = getDeepETag(lastUpdate);
		if (request.checkNotModified(eTag)) {
			return null;
		}

		PaymentMethodDto result = paymentMethodCrudService.findDtoById(id);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
				.eTag(eTag)
				.body(paymentMethodHateoas.mapModel(result));
	}

	@Override
	@PostMapping()
	public ResponseEntity<PaymentMethodDto> insert(@Valid @RequestBody PaymentMethodDto dto) {
		log.debug("REST request to insert a new payment method: {}", dto);
		PaymentMethodDto paymentMethod = paymentMethodCrudService.insert(dto);
		paymentMethodHateoas.mapModel(paymentMethod);
		return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<PaymentMethodDto> update(@PathVariable Long id, @Valid @RequestBody PaymentMethodDto dto) {
		log.debug("REST request to update payment method with id {}: {}", id, dto);
		PaymentMethodDto paymentMethod = paymentMethodCrudService.update(dto, id);
		paymentMethodHateoas.mapModel(paymentMethod);
		return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete payment method with id {}", id);
		paymentMethodCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private String getDeepETag(OffsetDateTime lastUpdate) {
		String eTag = "0";
		if (Objects.nonNull(lastUpdate)) {
			eTag = String.valueOf(lastUpdate.toEpochSecond());
		}
		return eTag;
	}
}
