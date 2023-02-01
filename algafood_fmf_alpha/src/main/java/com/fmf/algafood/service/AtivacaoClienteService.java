package com.fmf.algafood.service;

import com.fmf.algafood.annotations.TipoDoNotificador;
import com.fmf.algafood.enums.NivelUrgencia;
import com.fmf.algafood.event.ClienteAtivadoEvent;
import com.fmf.algafood.model.Cliente;
import com.fmf.algafood.notification.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class AtivacaoClienteService {

	@TipoDoNotificador(NivelUrgencia.NORMAL)
	@Autowired
	private Notificador notificador;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@PostConstruct
	public void init() {
		System.out.println("AtivacaoClienteService - INIT");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("AtivacaoClienteService - DESTROY");
	}

	public void ativar(Cliente cliente) {
		cliente.ativar();

		applicationEventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
	}
}
