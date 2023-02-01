package com.fmf.algafood.service;

import com.fmf.algafood.model.Cliente;
import com.fmf.algafood.notification.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AtivacaoClienteService {

	@Autowired(required = false)
	private Notificador notificador;

	public void ativar(Cliente cliente) {
		cliente.ativar();

		if (Objects.nonNull(notificador)) {
			notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
		} else {
			System.out.println("Não existe notificador definido, mas cliente foi ativado!");
		}
	}
}
