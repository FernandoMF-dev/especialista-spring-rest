package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDto implements Serializable {
	@JsonIgnore
	private Long customerId;

	@NotNull(message = "order_insert.restaurant_id.not_null")
	private Long restaurantId;

	@NotNull(message = "order_insert.payment_method_id.not_null")
	private Long paymentMethodId;

	@NotEmpty(message = "order_insert.order_products.not_empty")
	@Valid
	private List<OrderProductInsertDto> orderProducts = new ArrayList<>();

	@NotNull(message = "order_insert.address.not_null")
	@Valid
	private AddressDto address;
}
