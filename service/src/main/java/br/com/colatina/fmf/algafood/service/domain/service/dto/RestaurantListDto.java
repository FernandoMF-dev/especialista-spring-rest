package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.api.model.view.RestaurantView;
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
	@JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
	private Long id;
	@JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
	private String name;
	private Double freightFee;
	@JsonView(RestaurantView.Summary.class)
	private Boolean active;
	@JsonView(RestaurantView.Summary.class)
	private Boolean open;
	private Long cuisineId;
	private String cuisineName;
}
