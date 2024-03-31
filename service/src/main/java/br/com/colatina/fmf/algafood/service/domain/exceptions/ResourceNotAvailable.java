package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotAvailable extends BusinessRule {
	public ResourceNotAvailable(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
}
