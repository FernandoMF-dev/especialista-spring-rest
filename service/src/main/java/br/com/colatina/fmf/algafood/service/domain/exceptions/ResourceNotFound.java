package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends BusinessRule {

	public ResourceNotFound(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

}
