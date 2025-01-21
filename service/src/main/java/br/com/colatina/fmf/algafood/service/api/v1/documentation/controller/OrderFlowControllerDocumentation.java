package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import org.springframework.http.ResponseEntity;

public interface OrderFlowControllerDocumentation {
	ResponseEntity<Void> confirm(String orderUuid);

	ResponseEntity<Void> deliver(String orderUuid);

	ResponseEntity<Void> cancel(String orderUuid);
}
