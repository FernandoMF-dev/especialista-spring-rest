package br.com.colatina.fmf.algafood.service.api.v2.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CityInputV2 {
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;

	@NotBlank(message = "city.name.not_blank")
	private String name;

	@NotNull(message = "state.id.not_null")
	private Long stateId;
}
