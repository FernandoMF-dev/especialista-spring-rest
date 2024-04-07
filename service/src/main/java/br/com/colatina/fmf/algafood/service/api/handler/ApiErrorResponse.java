package br.com.colatina.fmf.algafood.service.api.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private Instant timestamp;
}
