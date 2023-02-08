package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StateDto implements Serializable {
	private Long id;
	@NotBlank
	@Size(max = 2)
	private String acronym;
	@NotBlank
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
