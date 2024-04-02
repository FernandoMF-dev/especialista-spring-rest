package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BusinessRuleException extends ResponseStatusException {
	protected BusinessRuleException(String reason, HttpStatus responseStatus) {
		super(responseStatus, reason);
	}
}
