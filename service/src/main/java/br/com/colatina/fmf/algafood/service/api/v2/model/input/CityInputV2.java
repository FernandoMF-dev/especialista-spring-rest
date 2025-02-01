package br.com.colatina.fmf.algafood.service.api.v2.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Input <City> [V2]", description = "Representation model for creating or updating a city")
@Getter
@Setter
public class CityInputV2 {
	@Schema(description = "Acronym of the city", example = "VIX")
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;

	@Schema(description = "Full name of the city", example = "Vitória")
	@NotBlank(message = "city.name.not_blank")
	private String name;

	@Schema(description = "ID of the state on which the city is located", example = "1")
	@NotNull(message = "state.id.not_null")
	private Long stateId;
}
