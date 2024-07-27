package br.com.colatina.fmf.algafood.service.api.documentation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("Pageable")
public class PageableModelOpenApi {
	@ApiModelProperty(value = "Page number (starts at 0)", example = "0")
	private int page;
	@ApiModelProperty(value = "Number of elements per page", example = "10")
	private int size;
	@ApiModelProperty(value = "Property name for sorting", example = "name/ASC")
	private List<String> sort;
}
