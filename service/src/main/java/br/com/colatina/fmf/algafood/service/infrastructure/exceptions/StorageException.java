package br.com.colatina.fmf.algafood.service.infrastructure.exceptions;

public class StorageException extends RuntimeException {
	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
