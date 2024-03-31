package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.repository.PaymentMethodRepository;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.PaymentMethodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodCrudService {
	private final PaymentMethodRepository paymentMethodRepository;
	private final PaymentMethodMapper paymentMethodMapper;

	public PaymentMethod findEntityById(Long id) {
		return paymentMethodRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Payment method %d not found", id)));
	}
}
