package br.com.colatina.fmf.algafood.service.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessRuleException extends RuntimeException {
	private final HttpStatus responseStatus;

	protected BusinessRuleException(String message, HttpStatus responseStatus) {
		super(message);
		this.responseStatus = responseStatus;
	}
}
