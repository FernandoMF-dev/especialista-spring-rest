package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessRuleException {
	public ResourceNotFoundException(String reason) {
		super(reason, null, HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(ResourceNotFoundException ex, HttpStatus httpStatus) {
		super(ex.getMessage(), ex.getMessageArgs(), httpStatus);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.RESOURCE_NOT_FOUND;
	}
}
