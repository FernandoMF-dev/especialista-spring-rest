package br.com.colatina.fmf.algafood.service.domain.exceptions;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorType;
import org.springframework.http.HttpStatus;

public class ConflictualResourceStatusException extends BusinessRuleException {
	public ConflictualResourceStatusException(String reason, Object... args) {
		super(reason, args, HttpStatus.CONFLICT);
	}

	@Override
	public ApiErrorType getApiErrorType() {
		return ApiErrorType.CONFLICTUAL_RESOURCE_STATUS;
	}
}
