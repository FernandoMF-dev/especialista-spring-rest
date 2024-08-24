package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.api.model.view.RestaurantView;
import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@ApiModel(value = "Restaurant (Listed)", description = "Representation model for a restaurant when displayed in a list")
@Relation(collectionRelation = "restaurants")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListDto extends RepresentationModel<RestaurantListDto> implements Serializable {
	@ApiModelProperty(value = "ID of the restaurant", example = "1")
	@EqualsAndHashCode.Include
	@SortableField
	@JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
	private Long id;

	@ApiModelProperty(value = "Name of the restaurant", example = "Burger King")
	@SortableField
	@JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
	private String name;

	@ApiModelProperty(value = "Freight fee of the restaurant", example = "5.00")
	@SortableField
	private Double freightFee;

	@ApiModelProperty(value = "Active status of the restaurant", example = "true")
	@JsonView(RestaurantView.Summary.class)
	private Boolean active;

	@ApiModelProperty(value = "Open status of the restaurant", example = "true")
	@JsonView(RestaurantView.Summary.class)
	private Boolean open;

	@ApiModelProperty(value = "ID of the cuisine", example = "1")
	@SortableField(translation = "cuisine.id")
	private Long cuisineId;

	@ApiModelProperty(value = "Name of the cuisine", example = "Italian")
	@SortableField(translation = "cuisine.name")
	private String cuisineName;
}
