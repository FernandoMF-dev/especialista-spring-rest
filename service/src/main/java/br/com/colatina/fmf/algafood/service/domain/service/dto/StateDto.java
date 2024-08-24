package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "State", description = "Representation model for state")
@Relation(collectionRelation = "states")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StateDto extends RepresentationModel<StateDto> implements Serializable {
	@NotNull(message = "state.id.not_null", groups = ValidationGroups.RequiredState.class)
	private Long id;
	@ApiModelProperty(value = "Acronym of the state", example = "ES", required = true)
	@NotBlank(message = "state.acronym.not_null")
	@Size(max = 2, message = "state.acronym.max_size")
	private String acronym;
	@ApiModelProperty(value = "Full name of the state", example = "Espírito Santo", required = true)
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
