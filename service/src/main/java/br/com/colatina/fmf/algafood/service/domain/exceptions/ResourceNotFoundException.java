package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Recurso não encontrado")
public class ResourceNotFoundException extends BusinessRuleException {

	public ResourceNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(String message, HttpStatus httpStatus) {
		super(message, httpStatus);
	}

}
