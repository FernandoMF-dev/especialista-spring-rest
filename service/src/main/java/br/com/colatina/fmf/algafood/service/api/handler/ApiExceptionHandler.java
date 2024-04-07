package br.com.colatina.fmf.algafood.service.api.handler;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
import io.micrometer.core.lang.Nullable;
import liquibase.repackaged.org.apache.commons.lang3.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
	public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
		ApiErrorType type = ex.getApiErrorType();
		String detail = ObjectUtils.defaultIfNull(ex.getReason(), ex.getStatus().getReasonPhrase());
		ApiErrorResponse body = createApiErrorResponseBuilder(ex.getStatus(), type, detail).build();
		return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String detail = "The request body is invalid. Check for syntax errors.";
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
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

	private ApiErrorResponse.ApiErrorResponseBuilder createApiErrorResponseBuilder(HttpStatus status, ApiErrorType type, String detail) {
		return ApiErrorResponse.builder()
				.status(status.value())
				.timestamp(Instant.now())
				.type(ERROR_TYPE_URI + type.getPath())
				.title(type.getTitle())
				.detail(detail);
	}
}
