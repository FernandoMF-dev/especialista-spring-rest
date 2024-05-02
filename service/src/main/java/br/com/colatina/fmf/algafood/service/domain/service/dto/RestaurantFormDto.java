package br.com.colatina.fmf.algafood.service.domain.service.dto;

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

@Getter
@Setter
@ToString
public class RestaurantFormDto implements Serializable {
	private Long id;
	@NotBlank(message = "restaurant.name.not_blank")
	private String name;
	@NotNull(message = "restaurant.freight_fee.not_null")
	@PositiveOrZero(message = "restaurant.freight_fee.positive_or_zero")
	private Double freightFee;
	@NotNull(message = "restaurant.cuisine.not_null")
	private Long cuisineId;
	@NotEmpty(message = "restaurant.payment_methods.not_empty")
	private Set<Long> paymentMethods = new HashSet<>();
	@Valid
	private AddressDto address;
}
