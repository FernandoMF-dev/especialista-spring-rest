package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Schema(name = "Model <Restaurant (Listed)>", description = "Representation model for a restaurant when displayed in a list")
@Relation(collectionRelation = "restaurants")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListDto extends RepresentationModel<RestaurantListDto> implements Serializable {
	@Schema(description = "ID of the restaurant", example = "1")
	@EqualsAndHashCode.Include
	@SortableField
	private Long id;

	@Schema(description = "Name of the restaurant", example = "Burger King")
	@SortableField
	private String name;

	@Schema(description = "Freight fee of the restaurant", example = "5.00")
	@SortableField
	private Double freightFee;

	@Schema(description = "Active status of the restaurant", example = "true")
	private Boolean active;

	@Schema(description = "Open status of the restaurant", example = "true")
	private Boolean open;

	@Schema(description = "ID of the cuisine", example = "1")
	@SortableField(translation = "cuisine.id")
	private Long cuisineId;

	@Schema(description = "Name of the cuisine", example = "Italian")
	@SortableField(translation = "cuisine.name")
	private String cuisineName;
}
