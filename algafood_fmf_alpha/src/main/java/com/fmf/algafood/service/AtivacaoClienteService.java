package com.fmf.algafood.service;

import com.fmf.algafood.model.Cliente;
import com.fmf.algafood.notification.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AtivacaoClienteService {

	@Autowired
	private List<Notificador> notificadors;

	public void ativar(Cliente cliente) {
		cliente.ativar();
		notificadors.forEach(notificador -> notificador.notificar(cliente, "Seu cadastro no sistema está ativo!"));
	}
}
