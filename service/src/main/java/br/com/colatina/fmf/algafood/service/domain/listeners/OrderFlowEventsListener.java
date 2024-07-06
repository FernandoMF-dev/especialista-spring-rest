package br.com.colatina.fmf.algafood.service.domain.listeners;

import br.com.colatina.fmf.algafood.service.domain.events.OrderConfirmedEvent;
import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFlowEventsListener {
	private final EmailSendService emailSendService;

	@EventListener
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
