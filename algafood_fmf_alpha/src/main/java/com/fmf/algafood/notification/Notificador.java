package com.fmf.algafood.notification;

import com.fmf.algafood.model.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String mensagem);

}
