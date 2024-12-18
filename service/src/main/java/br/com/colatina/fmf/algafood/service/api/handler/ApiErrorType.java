package br.com.colatina.fmf.algafood.service.api.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorType {
	RESOURCE_IN_USE("Resource is in use", "resource-in-use"),
	RESOURCE_NOT_AVAILABLE("Resource exists but it's not available for the operation", "resource-not-available"),
	RESOURCE_NOT_FOUND("Resource not found", "resource-not-found"),
	DUPLICATE_RESOURCE("Duplicate resource", "duplicate-resource"),
	CONFLICTUAL_RESOURCE_STATUS("Conflictual resource status", "conflictual-resource-status"),
	MESSAGE_BODY_NOT_READABLE("Incomprehensible message", "incomprehensible-message"),
	INVALID_REQUEST_PARAM("Invalid parameter", "invalid-param"),
	INTERNAL_SERVER_ERROR("Internal server error", "internal-server-error"),
	CONSTRAINT_VIOLATION("Constraint violation", "constraint-violation"),
	ACCESS_DENIED("Access denied", "access-denied");

	private final String title;
	private final String path;
}
