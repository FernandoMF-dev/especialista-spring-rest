package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Schema(name = "Model <State>", description = "Representation model for state")
@Relation(collectionRelation = "states")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class StateDto extends RepresentationModel<StateDto> implements Serializable {
	@Schema(description = "ID of the state", example = "1")
	@NotNull(message = "state.id.not_null", groups = ValidationGroups.RequiredState.class)
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Acronym of the state", example = "ES")
	@NotBlank(message = "state.acronym.not_null")
	@Size(max = 2, message = "state.acronym.max_size")
	private String acronym;

	@Schema(description = "Full name of the state", example = "Espírito Santo")
	@NotBlank(message = "state.name.not_blank")
	private String name;
}
