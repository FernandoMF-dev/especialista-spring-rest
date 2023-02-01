package com.fmf.algafood.notification;

import com.fmf.algafood.model.Cliente;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("email")
@Component
public class NotificadorEmail implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {

		System.out.printf("Notificando %s através do e-mail %s: %s%n",
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
