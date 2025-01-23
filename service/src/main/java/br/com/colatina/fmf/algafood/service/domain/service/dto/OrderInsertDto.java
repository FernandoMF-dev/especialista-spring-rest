package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(name = "Model <Order (Input)>", description = "Representation model for emitting a new order")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDto implements Serializable {
	@Schema(hidden = true)
	@JsonIgnore
	private Long customerId;

	@Schema(description = "ID of the restaurant receiving the order", example = "1")
	@NotNull(message = "order_insert.restaurant_id.not_null")
	private Long restaurantId;

	@Schema(description = "ID of the payment method selected by the client to pay for the order", example = "1")
	@NotNull(message = "order_insert.payment_method_id.not_null")
	private Long paymentMethodId;

	@Schema(description = "List of products in the order")
	@NotEmpty(message = "order_insert.order_products.not_empty")
	@Valid
	private List<OrderProductInsertDto> orderProducts = new ArrayList<>();

	@Schema(description = "Delivery address for the order")
	@NotNull(message = "order_insert.address.not_null")
	@Valid
	private AddressDto address;
}
