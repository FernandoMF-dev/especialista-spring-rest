package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BusinessRuleException extends ResponseStatusException {
	protected BusinessRuleException(String reason, HttpStatus responseStatus) {
		super(responseStatus, reason);
	}

	public abstract ApiErrorType getApiErrorType();
}
