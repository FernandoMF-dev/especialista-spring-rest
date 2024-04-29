package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.repository.PaymentMethodRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.PaymentMethodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodCrudService {
	public static final String ERROR_MSG_NOT_FOUND = "payment_method.not_found";
	private final PaymentMethodRepository paymentMethodRepository;
	private final PaymentMethodMapper paymentMethodMapper;

	public List<PaymentMethodDto> findAll() {
		return paymentMethodRepository.findAllDto();
	}

	public PaymentMethodDto findDtoById(Long id) {
		return paymentMethodRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ERROR_MSG_NOT_FOUND));
	}

	public PaymentMethod findEntityById(Long id) {
		return paymentMethodRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(ERROR_MSG_NOT_FOUND));
	}

	public void verifyExistence(List<Long> ids) {
		if (paymentMethodRepository.findAllById(ids).size() < ids.size()) {
			throw new ResourceNotFoundException(ERROR_MSG_NOT_FOUND);
		}
	}

	public PaymentMethodDto insert(PaymentMethodDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public PaymentMethodDto update(PaymentMethodDto dto, Long id) {
		PaymentMethodDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		PaymentMethod saved = findEntityById(id);
		saved.setExcluded(true);
		paymentMethodRepository.save(saved);
	}

	private PaymentMethodDto save(PaymentMethodDto dto) {
		PaymentMethod entity = paymentMethodMapper.toEntity(dto);
		entity = paymentMethodRepository.save(entity);
		return paymentMethodMapper.toDto(entity);
	}
}
