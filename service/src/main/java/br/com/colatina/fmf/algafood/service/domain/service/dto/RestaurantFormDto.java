package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Schema(name = "Model <Restaurant (Input)>", description = "Representation model for creating or updating a restaurant")
@Getter
@Setter
@ToString
public class RestaurantFormDto implements Serializable {
	@Schema(description = "ID of the restaurant", example = "1", hidden = true)
	@JsonIgnore
	private Long id;

	@Schema(description = "Name of the restaurant", example = "Burger King")
	@NotBlank(message = "restaurant.name.not_blank")
	private String name;

	@Schema(description = "Freight fee of the restaurant", example = "5.00")
	@NotNull(message = "restaurant.freight_fee.not_null")
	@PositiveOrZero(message = "restaurant.freight_fee.positive_or_zero")
	private Double freightFee;

	@Schema(description = "ID of the cuisine", example = "1")
	@NotNull(message = "restaurant.cuisine.not_null")
	private Long cuisineId;

	@Schema(description = "Set of payment method IDs")
	@NotEmpty(message = "restaurant.payment_methods.not_empty")
	private Set<Long> paymentMethods = new HashSet<>();

	@Schema(description = "Address of the restaurant")
	@Valid
	private AddressDto address;
}
