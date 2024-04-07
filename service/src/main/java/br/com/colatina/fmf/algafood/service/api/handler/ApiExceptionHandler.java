package br.com.colatina.fmf.algafood.service.api.handler;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<?> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getReason(), new HttpHeaders(), ex.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		final Map<String, Object> errorBody = new LinkedHashMap<>();

		errorBody.put("timestamp", Instant.now());
		errorBody.put("status", status.value());
		errorBody.put("message", status.getReasonPhrase());

		if (body instanceof String) {
			errorBody.put("message", body);
		}

		return super.handleExceptionInternal(ex, errorBody, headers, status, request);
	}
}
