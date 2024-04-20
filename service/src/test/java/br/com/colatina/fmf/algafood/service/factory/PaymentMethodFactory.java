package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.repository.PaymentMethodRepository;
import br.com.colatina.fmf.algafood.service.domain.service.PaymentMethodCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.PaymentMethodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodFactory extends BaseEntityFactory<PaymentMethod> {
	@Autowired
	PaymentMethodMapper paymentMethodMapper;
	@Autowired
	PaymentMethodCrudService paymentMethodCrudService;
	@Autowired
	PaymentMethodRepository paymentMethodRepository;

	@Override
	public PaymentMethod createEntity() {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setDescription(String.format("Payment Method %d", System.currentTimeMillis()));
		return paymentMethod;
	}

	@Override
	protected PaymentMethod persist(PaymentMethod entity) {
		return paymentMethodRepository.save(entity);
	}

	@Override
	public PaymentMethod getById(Long id) {
		return paymentMethodRepository.findById(id).orElse(null);
	}
}
