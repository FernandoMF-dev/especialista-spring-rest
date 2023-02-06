package br.com.colatina.fmf.algafood.service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceInUse extends BusinessRule {

	public ResourceInUse(String message) {
		super(message, HttpStatus.CONFLICT);
	}

}
