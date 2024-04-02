package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotAvailableException extends BusinessRuleException {
	public ResourceNotAvailableException(String reason) {
		super(reason, HttpStatus.BAD_REQUEST);
	}
}
