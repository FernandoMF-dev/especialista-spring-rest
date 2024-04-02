package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Recurso em uso")
public class ResourceInUseException extends BusinessRuleException {

	public ResourceInUseException(String message) {
		super(message, HttpStatus.CONFLICT);
	}

}
