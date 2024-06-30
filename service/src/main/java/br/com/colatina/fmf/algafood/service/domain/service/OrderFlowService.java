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
	private final EmailSendService emailSendService;

	public void confirm(String orderUuid) {
		Order order = orderCrudService.findEntityByUuid(orderUuid);
		order.confirm();

		EmailSendService.Email email = EmailSendService.Email.builder()
				.subject(order.getRestaurant().getName() + " - Pedido confirmado")
				.body("confirmed-order.email.html")
				.recipient(order.getUser().getEmail())
				.variable("order", order)
				.build();
		emailSendService.send(email);
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
