package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class ResourceInUseException extends BusinessRuleException {
	public ResourceInUseException(String reason) {
		super(reason, null, HttpStatus.CONFLICT);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.RESOURCE_IN_USE;
	}
}
