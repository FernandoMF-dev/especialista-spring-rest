package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "Model <Restaurant (Input)>", description = "Representation model for creating or updating a restaurant")
@Getter
@Setter
@ToString
public class RestaurantFormDto implements Serializable {
	@ApiModelProperty(value = "ID of the restaurant", example = "1", hidden = true)
	@JsonIgnore
	private Long id;

	@ApiModelProperty(value = "Name of the restaurant", example = "Burger King", required = true)
	@NotBlank(message = "restaurant.name.not_blank")
	private String name;

	@ApiModelProperty(value = "Freight fee of the restaurant", example = "5.00", required = true)
	@NotNull(message = "restaurant.freight_fee.not_null")
	@PositiveOrZero(message = "restaurant.freight_fee.positive_or_zero")
	private Double freightFee;

	@ApiModelProperty(value = "ID of the cuisine", example = "1", required = true)
	@NotNull(message = "restaurant.cuisine.not_null")
	private Long cuisineId;

	@ApiModelProperty(value = "Set of payment method IDs", required = true)
	@NotEmpty(message = "restaurant.payment_methods.not_empty")
	private Set<Long> paymentMethods = new HashSet<>();

	@ApiModelProperty(value = "Address of the restaurant")
	@Valid
	private AddressDto address;
}
