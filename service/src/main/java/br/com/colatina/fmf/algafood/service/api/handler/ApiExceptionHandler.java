package br.com.colatina.fmf.algafood.service.api.handler;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.micrometer.core.lang.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String ERROR_TYPE_URI = "https://fmf.algafood.com.br/";

	private final Map<Class<? extends Throwable>, InvalidBodyFormatHandler> rootExceptionsHandlers;

	public ApiExceptionHandler() {
		this.rootExceptionsHandlers = new HashMap<>();

		this.rootExceptionsHandlers.put(InvalidFormatException.class,
				(rootCause, headers, status, request) -> handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request));
		this.rootExceptionsHandlers.put(IgnoredPropertyException.class,
				(rootCause, headers, status, request) -> handlePropertyBindingException((IgnoredPropertyException) rootCause, headers, status, request));
		this.rootExceptionsHandlers.put(UnrecognizedPropertyException.class,
				(rootCause, headers, status, request) -> handlePropertyBindingException((UnrecognizedPropertyException) rootCause, headers, status, request));
		this.rootExceptionsHandlers.put(MethodArgumentTypeMismatchException.class,
				(rootCause, headers, status, request) -> handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) rootCause, headers, status, request));
	}

	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
		ApiErrorType type = ex.getApiErrorType();
		String detail = ObjectUtils.defaultIfNull(ex.getReason(), ex.getStatus().getReasonPhrase());
		ApiErrorResponse body = createApiErrorResponseBuilder(ex.getStatus(), type, detail).build();
		return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (this.rootExceptionsHandlers.containsKey(rootCause.getClass())) {
			return this.rootExceptionsHandlers.get(rootCause.getClass()).handle(rootCause, headers, status, request);
		}

		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String detail = "The request body is invalid. Check for syntax errors.";
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (this.rootExceptionsHandlers.containsKey(ex.getClass())) {
			return this.rootExceptionsHandlers.get(ex.getClass()).handle(ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
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

	private String formatPropertyPath(List<JsonMappingException.Reference> path) {
		return path.stream()
				.map(JsonMappingException.Reference::getFieldName)
				.collect(Collectors.joining("."));
	}

	// <editor-fold defaultstate="collapsed" desc="Root or specific exceptions handlers">
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String propertyPath = formatPropertyPath(ex.getPath());
		String detail = String.format("Property '%s' has been assigned the value '%s', which is an invalid type. Correct and enter a value compatible with type '%s'",
				propertyPath, ex.getValue(), ex.getTargetType().getSimpleName());
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String propertyPath = formatPropertyPath(ex.getPath());
		String detail = String.format("Property '%s' does not exist. Check for possible error or remove it entirely, and then try again.", propertyPath);
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String detail = String.format("The URL parameter '%s' has been assigned the value '%s', which is an invalid type. Correct and enter a value compatible with type '%s'",
				ex.getPropertyName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	// </editor-fold>

	private interface InvalidBodyFormatHandler {
		ResponseEntity<Object> handle(Throwable rootCause, HttpHeaders headers, HttpStatus status, WebRequest request);
	}
}
