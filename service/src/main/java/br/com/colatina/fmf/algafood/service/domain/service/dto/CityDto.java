package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;

@ApiModel(value = "City", description = "Representation model for city")
@Relation(collectionRelation = "cities")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class CityDto extends RepresentationModel<CityDto> implements Serializable {
	@NotNull(message = "city.id.not_null", groups = ValidationGroups.RequiredCity.class)
	@EqualsAndHashCode.Include
	private Long id;

	@ApiModelProperty(value = "Acronym of the city", example = "VIX", required = true)
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;

	@ApiModelProperty(value = "Full name of the city", example = "Vitória", required = true)
	@NotBlank(message = "city.name.not_blank")
	private String name;

	@ApiModelProperty(value = "State on which the city is located", required = true)
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
