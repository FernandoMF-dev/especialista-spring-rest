package br.com.colatina.fmf.algafood.service.domain.listeners;

import br.com.colatina.fmf.algafood.service.domain.events.OrderCanceledEvent;
import br.com.colatina.fmf.algafood.service.domain.events.OrderConfirmedEvent;
import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderFlowEventsListener {
	private final EmailSendService emailSendService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void whenOrderConfirmed(OrderConfirmedEvent event) {
		var order = event.order();

		var email = EmailSendService.Email.builder()
				.subject(order.getRestaurant().getName() + " - Pedido confirmado")
				.body("confirmed-order.email.html")
				.recipient(order.getCustomer().getEmail())
				.variable("order", order)
				.build();

		emailSendService.send(email);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void whenOrderCanceled(OrderCanceledEvent event) {
		var order = event.order();

		var email = EmailSendService.Email.builder()
				.subject(order.getRestaurant().getName() + " - Pedido cancelado")
				.body("canceled-order.email.html")
				.recipient(order.getCustomer().getEmail())
				.variable("order", order)
				.build();

		emailSendService.send(email);
	}
}
