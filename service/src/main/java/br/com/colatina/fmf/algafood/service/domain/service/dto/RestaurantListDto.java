package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.api.model.view.RestaurantView;
import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListDto implements Serializable {
	@SortableField
	@JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
	private Long id;
	@SortableField
	@JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
	private String name;
	@SortableField
	private Double freightFee;
	@JsonView(RestaurantView.Summary.class)
	private Boolean active;
	@JsonView(RestaurantView.Summary.class)
	private Boolean open;
	@SortableField(translation = "cuisine.id")
	private Long cuisineId;
	@SortableField(translation = "cuisine.name")
	private String cuisineName;
}
