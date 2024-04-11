package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CityDto implements Serializable {
	private Long id;
	@NotNull(message = "The acronym can not be null")
	@Size(max = 5)
	private String acronym;
	@NotBlank(message = "Name can not be null and must contain at least one non-whitespace character")
	private String name;
	@NotNull(message = "The state can not be null")
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
