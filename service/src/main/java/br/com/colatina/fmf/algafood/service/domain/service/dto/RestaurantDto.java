package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto implements Serializable {
	private Long id;
	@NotBlank(message = "restaurant.name.not_blank")
	private String name;
	@NotNull(message = "restaurant.freight_fee.not_null")
	@PositiveOrZero(message = "restaurant.freight_fee.positive_or_zero")
	private Double freightFee;
	private LocalDateTime registrationDate;
	private LocalDateTime updateDate;
	@NotNull(message = "restaurant.active.not_null")
	private Boolean active;
	@NotNull(message = "restaurant.cuisine.not_null")
	@ConvertGroup(to = ValidationGroups.RequiredCuisine.class)
	@Valid
	private CuisineDto cuisine;
	@ConvertGroup(to = ValidationGroups.RequiredPaymentMethod.class)
	@Valid
	private List<PaymentMethodDto> paymentMethods = new ArrayList<>();
	private List<ProductDto> products = new ArrayList<>();
	@Valid
	private AddressDto address;

	public Long getCuisineId() {
		if (Objects.isNull(this.getCuisine())) {
			return null;
		}
		return this.getCuisine().getId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RestaurantDto)) {
			return false;
		}
		RestaurantDto that = (RestaurantDto) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
