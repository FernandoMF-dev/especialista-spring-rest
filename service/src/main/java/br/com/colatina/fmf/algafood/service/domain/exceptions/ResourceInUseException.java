package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceInUseException extends BusinessRuleException {

	public ResourceInUseException(String reason) {
		super(reason, HttpStatus.CONFLICT);
	}

}
