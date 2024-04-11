package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
	@NotBlank(message = "Name can not be null and must contain at least one non-whitespace character")
	private String name;
	@NotNull(message = "Freight fee can not be null")
	@PositiveOrZero(message = "Freight fee can not be lower than zero")
	private Double freightFee;
	private LocalDateTime registrationDate;
	private LocalDateTime updateDate;
	@NotNull(message = "The active field can not be null")
	private Boolean active;
	@NotNull(message = "Must specify the type of cuisine of the restaurant")
	private KitchenDto kitchen;
	private List<PaymentMethodDto> paymentMethods = new ArrayList<>();
	private List<ProductDto> products = new ArrayList<>();
	@Valid
	private AddressDto address;

	public Long getKitchenId() {
		if (Objects.isNull(this.getKitchen())) {
			return null;
		}
		return this.getKitchen().getId();
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
