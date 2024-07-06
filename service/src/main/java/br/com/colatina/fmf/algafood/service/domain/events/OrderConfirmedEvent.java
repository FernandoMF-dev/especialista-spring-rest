package br.com.colatina.fmf.algafood.service.domain.events;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {
	private final Order order;
}
