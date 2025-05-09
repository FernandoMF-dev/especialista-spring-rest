package br.com.colatina.fmf.algafood.service.api.handler;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.InfrastructureException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	public static final String GENERIC_ERROR_USER_MSG = "error.default.user_msg";

	private final MessageSource messageSource;
	private final Map<Class<? extends Throwable>, InvalidBodyFormatHandler> rootExceptionsHandlers;

	@Value("${algafood.error.type.url}")
	private String errorTypeUrl = "";

	public ApiExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
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
	@Nullable
	public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
		ApiErrorType type = ex.getApiErrorType();
		String detail = ObjectUtils.defaultIfNull(ex.getReason(), HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase());
		detail = getMessageSourceIfAvailable(detail, ex.getMessageArgs());

		ApiErrorResponse body = createApiErrorResponseBuilder(ex.getStatusCode(), type, detail).userMessage(detail).build();
		return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatusCode(), request);
	}

	@ExceptionHandler(InfrastructureException.class)
	@Nullable
	public ResponseEntity<Object> handleInfrastructureException(InfrastructureException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiErrorType type = ApiErrorType.INTERNAL_SERVER_ERROR;
		String detail = getMessageSourceIfAvailable(ex.getMessage());
		String userMessage = GENERIC_ERROR_USER_MSG;
		userMessage = getMessageSourceIfAvailable(userMessage);

		log.error(userMessage, ex);
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).userMessage(userMessage).build();
		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@Nullable
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ApiErrorType type = ApiErrorType.ACCESS_DENIED;
		String detail = getMessageSourceIfAvailable(ex.getMessage());
		String userMessage = getMessageSourceIfAvailable("error.http_request.access_denied");

		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).userMessage(userMessage).build();
		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(Exception.class)
	@Nullable
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiErrorType type = ApiErrorType.INTERNAL_SERVER_ERROR;
		String detail = GENERIC_ERROR_USER_MSG;
		detail = getMessageSourceIfAvailable(detail);

		log.error(detail, ex);
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiErrorType type = ApiErrorType.CONSTRAINT_VIOLATION;
		String detail = getMessageSourceIfAvailable("error.http_request.body_property.constraint_violation");
		List<ApiErrorResponse.FieldError> fieldErrors = formatUrlParamFieldErrors(ex);
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).userMessage(detail).fields(fieldErrors).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiErrorType type = ApiErrorType.RESOURCE_NOT_FOUND;
		String detail = getMessageSourceIfAvailable("error.default.not_found", new String[]{ex.getRequestURL()});
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (this.rootExceptionsHandlers.containsKey(rootCause.getClass())) {
			return this.rootExceptionsHandlers.get(rootCause.getClass()).handle(rootCause, headers, status, request);
		}

		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String detail = getMessageSourceIfAvailable("error.http_request.default.not_readable");
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		if (this.rootExceptionsHandlers.containsKey(ex.getClass())) {
			return this.rootExceptionsHandlers.get(ex.getClass()).handle(ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		if (Objects.isNull(body)) {
			body = ApiErrorResponse.builder()
					.status(status.value())
					.timestamp(Instant.now())
					.title(HttpStatus.valueOf(status.value()).getReasonPhrase())
					.userMessage(getMessageSourceIfAvailable(GENERIC_ERROR_USER_MSG))
					.build();
		} else if (body instanceof String stringBody) {
			body = ApiErrorResponse.builder()
					.status(status.value())
					.timestamp(Instant.now())
					.title(getMessageSourceIfAvailable(stringBody))
					.userMessage(getMessageSourceIfAvailable(GENERIC_ERROR_USER_MSG))
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ApiErrorResponse.ApiErrorResponseBuilder createApiErrorResponseBuilder(HttpStatusCode status, ApiErrorType type, String detail) {
		return ApiErrorResponse.builder()
				.status(status.value())
				.timestamp(Instant.now())
				.type(errorTypeUrl + type.getPath())
				.title(type.getTitle())
				.detail(detail)
				.userMessage(getMessageSourceIfAvailable(GENERIC_ERROR_USER_MSG));
	}

	private String formatPropertyPath(List<JsonMappingException.Reference> path) {
		return path.stream()
				.map(JsonMappingException.Reference::getFieldName)
				.collect(Collectors.joining("."));
	}

	private List<ApiErrorResponse.FieldError> formatUrlParamFieldErrors(MethodArgumentNotValidException ex) {
		return ex.getFieldErrors().stream()
				.map(fieldError -> {
					String message = getFieldErrorMessage(fieldError);
					return new ApiErrorResponse.FieldError(fieldError.getField(), message);
				})
				.toList();
	}

	private String getFieldErrorMessage(FieldError fieldError) {
		var message = ObjectUtils.defaultIfNull(fieldError.getDefaultMessage(), "error.property.constraint_violation");
		var arguments = fieldError.getArguments();
		if (Objects.nonNull(arguments)) {
			arguments = Arrays.stream(arguments).filter(value -> !(value instanceof DefaultMessageSourceResolvable)).toArray();
		}
		return getMessageSourceIfAvailable(message, arguments);
	}

	private String getMessageSourceIfAvailable(String message) {
		return getMessageSourceIfAvailable(message, null);
	}

	private String getMessageSourceIfAvailable(String message, @Nullable Object[] args) {
		try {
			return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			return message;
		}
	}

	// <editor-fold defaultstate="collapsed" desc="Root or specific exceptions handlers">
	@Nullable
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String propertyPath = formatPropertyPath(ex.getPath());
		String detail = getMessageSourceIfAvailable("error.http_request.body_property.type_mismatch", new Object[]{propertyPath, ex.getValue(), ex.getTargetType().getSimpleName()});
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Nullable
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String propertyPath = formatPropertyPath(ex.getPath());
		String detail = getMessageSourceIfAvailable("error.http_request.body_property.not_found", new Object[]{propertyPath});
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Nullable
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiErrorType type = ApiErrorType.MESSAGE_BODY_NOT_READABLE;
		String detail = getMessageSourceIfAvailable("error.http_request.url_param.type_mismatch", new Object[]{ex.getPropertyName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()});
		ApiErrorResponse body = createApiErrorResponseBuilder(status, type, detail).build();
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	// </editor-fold>

	private interface InvalidBodyFormatHandler {
		ResponseEntity<Object> handle(Throwable rootCause, HttpHeaders headers, HttpStatusCode status, WebRequest request);
	}
}
