package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.repository.PaymentMethodRepository;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.PaymentMethodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodCrudService {
	private final PaymentMethodRepository paymentMethodRepository;
	private final PaymentMethodMapper paymentMethodMapper;

	public PaymentMethod findEntityById(Long id) {
		return paymentMethodRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("payment_method.not_found"));
	}

	public void verifyExistence(List<Long> ids) {
		if (paymentMethodRepository.findAllById(ids).size() < ids.size()) {
			throw new ResourceNotFoundException("payment_method.not_found");
		}
	}
}
