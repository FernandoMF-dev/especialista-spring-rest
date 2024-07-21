package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "City", description = "Representation model for city")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CityDto implements Serializable {
	@ApiModelProperty(value = "Unique ID of the city", example = "1")
	@NotNull(message = "city.id.not_null", groups = ValidationGroups.RequiredCity.class)
	private Long id;
	@ApiModelProperty(value = "Acronym of the city", example = "VIX")
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;
	@ApiModelProperty(value = "Full name of the city", example = "Vitória")
	@NotBlank(message = "city.name.not_blank")
	private String name;
	@ApiModelProperty(value = "State on which the city is located")
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CityDto)) {
			return false;
		}
		CityDto cityDto = (CityDto) o;
		return id.equals(cityDto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
