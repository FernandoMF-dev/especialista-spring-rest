package br.com.colatina.fmf.algafood.service.domain.listeners;

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
		var order = event.getOrder();

		var email = EmailSendService.Email.builder()
				.subject(order.getRestaurant().getName() + " - Pedido confirmado")
				.body("confirmed-order.email.html")
				.recipient(order.getUser().getEmail())
				.variable("order", order)
				.build();

		emailSendService.send(email);
	}
}
