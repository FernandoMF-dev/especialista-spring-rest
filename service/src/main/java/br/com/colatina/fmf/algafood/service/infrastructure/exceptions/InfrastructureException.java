package br.com.colatina.fmf.algafood.service.infrastructure.exceptions;

public abstract class InfrastructureException extends RuntimeException {
	protected InfrastructureException(String message, Throwable cause) {
		super(message, cause);
	}
}
