package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class StateDto implements Serializable {
	@NotNull(message = "state.id.not_null", groups = ValidationGroups.RequiredState.class)
	private Long id;
	@NotBlank(message = "state.acronym.not_null")
	@Size(max = 2, message = "state.acronym.max_size")
	private String acronym;
	@NotBlank(message = "state.name.not_blank")
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof StateDto)) {
			return false;
		}
		StateDto stateDto = (StateDto) o;
		return id.equals(stateDto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
