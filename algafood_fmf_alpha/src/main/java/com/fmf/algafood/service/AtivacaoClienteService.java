package com.fmf.algafood.service;

import com.fmf.algafood.model.Cliente;
import com.fmf.algafood.notification.Notificador;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {
	private final Notificador notificador;

	public AtivacaoClienteService(Notificador notificador) {
		this.notificador = notificador;

		System.out.println("AtivacaoClienteService: " + notificador);
	}

	public void ativar(Cliente cliente) {
		cliente.ativar();

		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}
