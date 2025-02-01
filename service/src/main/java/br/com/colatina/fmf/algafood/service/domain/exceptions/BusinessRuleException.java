package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

@Getter
public abstract class BusinessRuleException extends ResponseStatusException {
	private final transient Object[] messageArgs;

	protected BusinessRuleException(String reason, @Nullable Object[] messageArgs, HttpStatus responseStatus) {
		super(responseStatus, reason);
		this.messageArgs = messageArgs;
	}

	public abstract ApiErrorType getApiErrorType();
}
