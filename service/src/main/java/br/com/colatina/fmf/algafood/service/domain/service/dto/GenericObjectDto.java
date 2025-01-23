package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Schema(name = "Model <GenericObject>", description = "Representation model for a generic object with only an ID and a name/description")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GenericObjectDto extends RepresentationModel<GenericObjectDto> implements Serializable {
	@Schema(description = "ID of the entity", example = "1")
	@SortableField
	private Long id;

	@Schema(description = "Name of the entity", example = "Sample Name")
	@SortableField
	private String name;
}
