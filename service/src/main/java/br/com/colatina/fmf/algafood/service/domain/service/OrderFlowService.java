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

	public void confirm(String orderUuid) {
		Order order = orderCrudService.findEntityByUuid(orderUuid);
		order.confirm();
	}

	public void deliver(String orderUuid) {
		Order order = orderCrudService.findEntityByUuid(orderUuid);
		order.deliver();
	}

	public void cancel(String orderUuid) {
		Order order = orderCrudService.findEntityByUuid(orderUuid);
		order.cancel();
	}
}
