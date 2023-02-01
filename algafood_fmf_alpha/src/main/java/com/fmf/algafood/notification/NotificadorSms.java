package com.fmf.algafood.notification;

import com.fmf.algafood.model.Cliente;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("sms")
@Component
public class NotificadorSms implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {

		System.out.printf("Notificando %s por SMS através do telefone %s: %s%n",
				cliente.getNome(), cliente.getTelefone(), mensagem);
	}

}
