package br.com.colatina.fmf.algafood.service.api.documentation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PageMetadata")
public class PageModelOpenApi {
	@ApiModelProperty(value = "Size of the page", example = "10")
	private Integer size;
	@ApiModelProperty(value = "Total number of elements", example = "50")
	private Long totalElements;
	@ApiModelProperty(value = "Total number of pages according to the `size` and `totalElements`", example = "5")
	private Integer totalPages;
	@ApiModelProperty(value = "Current page number (starts at 0)", example = "0")
	private Integer number;
}
