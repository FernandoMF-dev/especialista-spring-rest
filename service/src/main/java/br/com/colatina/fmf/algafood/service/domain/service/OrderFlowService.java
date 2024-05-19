package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderFlowService {
	private final OrderCrudService orderCrudService;

	public void confirm(Long orderId) {
		Order order = orderCrudService.findEntityById(orderId);
		order.confirm();
	}

	public void deliver(Long orderId) {
		Order order = orderCrudService.findEntityById(orderId);
		order.deliver();
	}

	public void cancel(Long orderId) {
		Order order = orderCrudService.findEntityById(orderId);
		order.cancel();
	}
}
