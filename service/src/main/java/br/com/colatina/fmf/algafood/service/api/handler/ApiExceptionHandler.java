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
import java.util.Objects;

@RequiredArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String ERROR_TYPE_URI = "https://fmf.algafood.com.br/";

	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<?> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
		final ApiErrorResponse body = ApiErrorResponse.builder()
				.status(ex.getStatus().value())
				.timestamp(Instant.now())
				.type(ERROR_TYPE_URI + ex.getPath())
				.title(ex.getTitle())
				.detail(ex.getReason())
				.build();

		return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (Objects.isNull(body)) {
			body = ApiErrorResponse.builder()
					.status(status.value())
					.timestamp(Instant.now())
					.title(status.getReasonPhrase())
					.build();
		} else if (body instanceof String) {
			body = ApiErrorResponse.builder()
					.status(status.value())
					.timestamp(Instant.now())
					.title((String) body)
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}
