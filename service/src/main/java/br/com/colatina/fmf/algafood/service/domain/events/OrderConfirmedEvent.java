package br.com.colatina.fmf.algafood.service.domain.events;

import br.com.colatina.fmf.algafood.service.domain.model.Order;

public record OrderConfirmedEvent(Order order) {
}
