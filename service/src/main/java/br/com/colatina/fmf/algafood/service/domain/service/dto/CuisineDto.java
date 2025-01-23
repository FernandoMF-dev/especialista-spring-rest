package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(name = "Model <Cuisine>", description = "Representation model for cuisine")
@Relation(collectionRelation = "cuisines")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonRootName("cuisine")
@NoArgsConstructor
@AllArgsConstructor
public class CuisineDto extends RepresentationModel<CuisineDto> implements Serializable {
	@Schema(description = "ID of the cuisine", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Name of the cuisine", example = "Italian")
	@NotBlank(message = "cuisine.name.not_blank")
	private String name;
}
