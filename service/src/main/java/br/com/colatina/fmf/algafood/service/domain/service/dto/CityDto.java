package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import java.io.Serializable;

@Schema(name = "Model <City>", description = "Representation model for city")
@Relation(collectionRelation = "cities")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class CityDto extends RepresentationModel<CityDto> implements Serializable {
	@Schema(description = "ID of the city", example = "1")
	@NotNull(message = "city.id.not_null", groups = ValidationGroups.RequiredCity.class)
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Acronym of the city", example = "VIX")
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;

	@Schema(description = "Full name of the city", example = "Vitória")
	@NotBlank(message = "city.name.not_blank")
	private String name;

	@Schema(description = "State on which the city is located")
	@NotNull(message = "city.state.not_null")
	@ConvertGroup(to = ValidationGroups.RequiredState.class)
	@Valid
	private StateDto state;

	public CityDto(Long id, String acronym, String name, Long stateId, String stateAcronym, String stateName) {
		this.id = id;
		this.acronym = acronym;
		this.name = name;
		this.state = new StateDto(stateId, stateAcronym, stateName);
	}
}
