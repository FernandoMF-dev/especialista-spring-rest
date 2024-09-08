package br.com.colatina.fmf.algafood.service.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "Input <City>", description = "Representation model for creating or updating a city")
@Getter
@Setter
public class CityInputV2 {
	@ApiModelProperty(value = "Acronym of the city", example = "VIX")
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;

	@ApiModelProperty(value = "Full name of the city", example = "Vitória")
	@NotBlank(message = "city.name.not_blank")
	private String name;

	@ApiModelProperty(value = "ID of the state on which the city is located", example = "1")
	@NotNull(message = "state.id.not_null")
	private Long stateId;
}
