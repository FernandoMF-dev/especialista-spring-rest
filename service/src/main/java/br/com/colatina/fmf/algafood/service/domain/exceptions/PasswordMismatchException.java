package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends BusinessRuleException {
	public PasswordMismatchException(String reason) {
		super(reason, null, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.CONSTRAINT_VIOLATION;
	}
}
