package com.fmf.algafood.service;

import com.fmf.algafood.model.Cliente;
import com.fmf.algafood.notification.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {
	private Notificador notificador;

	// Em uma classe com vários construtores disponíveis, essa anotação pode dizer ao Spring em qual deles fazer a injeção de dependencia
	@Autowired
	public AtivacaoClienteService(Notificador notificador) {
		this.notificador = notificador;
	}

	public AtivacaoClienteService(String exemplo) {
	}

	public void ativar(Cliente cliente) {
		cliente.ativar();

		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}
