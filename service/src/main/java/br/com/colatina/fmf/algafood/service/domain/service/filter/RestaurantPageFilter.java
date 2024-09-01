package br.com.colatina.fmf.algafood.service.domain.service.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel(value = "Filter <Restaurant>", description = "Filter model for paginating restaurants")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPageFilter implements Serializable {
	@ApiModelProperty(value = "Name of the restaurant", example = "Burger King")
	private String name;

	@ApiModelProperty(value = "Minimum freight fee", example = "5.00")
	private Double minFreightFee;

	@ApiModelProperty(value = "Maximum freight fee", example = "15.00")
	private Double maxFreightFee;

	@ApiModelProperty(value = "Active status of the restaurant", example = "true")
	private Boolean active;

	@ApiModelProperty(value = "ID of the cuisine", example = "1")
	private Long cuisineId;
}
