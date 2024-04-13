package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonRootName("cuisine")
@NoArgsConstructor
@AllArgsConstructor
public class CuisineDto implements Serializable {
	@NotNull(message = "cuisine.id.not_null", groups = ValidationGroups.RequiredCuisine.class)
	private Long id;
	@NotBlank(message = "cuisine.name.not_blank")
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CuisineDto)) {
			return false;
		}
		CuisineDto that = (CuisineDto) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
