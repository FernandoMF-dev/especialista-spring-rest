package com.fmf.algafood.notification;

import com.fmf.algafood.annotations.TipoDoNotificador;
import com.fmf.algafood.enums.NivelUrgencia;
import com.fmf.algafood.model.Cliente;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmailMock implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {

		System.out.printf("MOCK: Notificação seria enviada para %s através do e-mail %s: %s%n",
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
