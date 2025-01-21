package br.com.colatina.fmf.algafood.service.api.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private Instant timestamp;

	private String userMessage;
	private List<FieldError> fields;

	@Getter
	@Builder
	public static class FieldError {
		private String field;
		private String message;
	}
}
