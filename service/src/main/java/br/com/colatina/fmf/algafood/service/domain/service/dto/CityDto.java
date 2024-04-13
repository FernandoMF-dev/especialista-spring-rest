package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CityDto implements Serializable {
	@NotNull(message = "city.id.not_null", groups = ValidationGroups.RequiredCity.class)
	private Long id;
	@NotNull(message = "city.acronym.not_null")
	@Size(max = 5, message = "city.acronym.max_size")
	private String acronym;
	@NotBlank(message = "city.name.not_blank")
	private String name;
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
