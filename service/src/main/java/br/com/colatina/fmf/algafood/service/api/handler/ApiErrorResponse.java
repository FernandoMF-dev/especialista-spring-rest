package br.com.colatina.fmf.algafood.service.api.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Schema(name = "ApiErrorResponse")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
	@Schema(example = "400")
	private Integer status;

	@Schema(example = "https://fmf.algafood.com.br/errors/constraint-violation")
	private String type;

	@Schema(example = "Constraint violation")
	private String title;

	@Schema(example = "One or more fields are invalid. Fill in correctly and try again.")
	private String detail;

	@Schema(example = "2021-08-01T11:21:50.563Z")
	private Instant timestamp;

	@Schema(example = "One or more fields are invalid. Fill in correctly and try again.")
	private String userMessage;

	@Schema(description = "List of fields or objects with errors (optional)")
	private List<FieldError> fields;

	@Schema(name = "FieldError")
	@Getter
	@Builder
	public static class FieldError {
		@Schema(example = "name")
		private String field;

		@Schema(example = "The name is required")
		private String message;
	}
}
