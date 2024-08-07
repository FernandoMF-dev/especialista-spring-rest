package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
public class RestaurantDto implements Serializable {
	private Long id;
	private String name;
	private Double freightFee;
	private OffsetDateTime registrationDate;
	private OffsetDateTime updateDate;
	private Boolean active;
	private Boolean open;
	private CuisineDto cuisine;
	private List<ProductDto> products = new ArrayList<>();
	private Set<PaymentMethodDto> paymentMethods = new HashSet<>();
	private Set<UserDto> responsible = new HashSet<>();
	private AddressDto address;

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
