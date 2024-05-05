package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessRuleException {
	public ResourceNotFoundException(String reason) {
		this(reason, null, HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(String reason, Object... args) {
		super(reason, args, HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(ResourceNotFoundException ex, HttpStatus httpStatus) {
		super(ex.getReason(), ex.getMessageArgs(), httpStatus);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.RESOURCE_NOT_FOUND;
	}
}
