package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class ResourceNotAvailableException extends BusinessRuleException {
	public ResourceNotAvailableException(String reason) {
		super(reason, null, HttpStatus.BAD_REQUEST);
	}

	public ResourceNotAvailableException(String reason, Object... args) {
		super(reason, args, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.RESOURCE_NOT_AVAILABLE;
	}
}
