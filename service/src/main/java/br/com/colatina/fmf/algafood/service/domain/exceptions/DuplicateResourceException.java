package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BusinessRuleException {
	public DuplicateResourceException(String reason, Object... args) {
		super(reason, args, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.DUPLICATE_RESOURCE;
	}
}
