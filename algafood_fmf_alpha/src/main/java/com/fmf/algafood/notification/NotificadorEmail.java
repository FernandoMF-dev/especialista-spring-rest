package com.fmf.algafood.notification;

import com.fmf.algafood.annotations.TipoDoNotificador;
import com.fmf.algafood.enums.NivelUrgencia;
import com.fmf.algafood.model.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmail implements Notificador {

	@Value("${notificador.email.host-servidor}")
	private String host;
	@Value("${notificador.email.porta-servidor}")
	private Integer porta;

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.println("HOST: " + host);
		System.out.println("PORTA: " + porta);

		System.out.printf("Notificando %s através do e-mail %s: %s%n",
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
