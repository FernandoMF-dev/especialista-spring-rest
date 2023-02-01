package com.fmf.algafood.event;

import com.fmf.algafood.model.Cliente;

public class ClienteAtivadoEvent {

	private final Cliente cliente;

	public ClienteAtivadoEvent(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
