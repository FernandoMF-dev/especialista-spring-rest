package br.com.colatina.fmf.algafood.service.api.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
	@ApiModelProperty(example = "400")
	private Integer status;
	@ApiModelProperty(example = "https://fmf.algafood.com.br/errors/constraint-violation")
	private String type;
	@ApiModelProperty(example = "Constraint violation")
	private String title;
	@ApiModelProperty(example = "One or more fields are invalid. Fill in correctly and try again.")
	private String detail;
	@ApiModelProperty(example = "2021-08-01T00:00:00Z")
	private Instant timestamp;

	@ApiModelProperty(example = "One or more fields are invalid. Fill in correctly and try again.")
	private String userMessage;
	@ApiModelProperty("List of fields or objects with errors (optional)")
	private List<FieldError> fields;

	@Getter
	@Builder
	public static class FieldError {
		@ApiModelProperty(example = "name")
		private String field;
		@ApiModelProperty(example = "The name is required")
		private String message;
	}
}
