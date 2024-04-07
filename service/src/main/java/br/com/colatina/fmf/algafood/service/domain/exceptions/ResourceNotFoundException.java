package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessRuleException {
	public ResourceNotFoundException(String reason) {
		super(reason, HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(String reason, HttpStatus httpStatus) {
		super(reason, httpStatus);
	}

	@Override
	public String getTitle() {
		return "Resource not found";
	}

	@Override
	public String getPath() {
		return "resource-not-found";
	}
}
