package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	@NotBlank
	private String name;
	@NotNull
	private Double freightFee;
	private LocalDateTime registrationDate;
	private LocalDateTime updateDate;
	@NotNull
	private Boolean active;
	@NotNull
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
