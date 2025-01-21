package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListDto extends RepresentationModel<RestaurantListDto> implements Serializable {
	@EqualsAndHashCode.Include
	@SortableField
	private Long id;

	@SortableField
	private String name;

	@SortableField
	private Double freightFee;

	private Boolean active;

	private Boolean open;

	@SortableField(translation = "cuisine.id")
	private Long cuisineId;

	@SortableField(translation = "cuisine.name")
	private String cuisineName;
}
