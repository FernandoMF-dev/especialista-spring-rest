package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Recurso não disponível")
public class ResourceNotAvailableException extends BusinessRuleException {
	public ResourceNotAvailableException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
}
