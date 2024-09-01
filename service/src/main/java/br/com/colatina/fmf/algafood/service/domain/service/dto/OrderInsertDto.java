package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "Model <Order (Input)>", description = "Representation model for emitting a new order")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDto implements Serializable {
	@ApiModelProperty(value = "ID of the customer placing the order", example = "1", required = true)
	@NotNull(message = "order_insert.user_id.not_null")
	private Long customerId;

	@ApiModelProperty(value = "ID of the restaurant receiving the order", example = "1", required = true)
	@NotNull(message = "order_insert.restaurant_id.not_null")
	private Long restaurantId;

	@ApiModelProperty(value = "ID of the payment method selected by the client to pay for the order", example = "1", required = true)
	@NotNull(message = "order_insert.payment_method_id.not_null")
	private Long paymentMethodId;

	@ApiModelProperty(value = "List of products in the order", required = true)
	@NotEmpty(message = "order_insert.order_products.not_empty")
	@Valid
	private List<OrderProductInsertDto> orderProducts = new ArrayList<>();

	@ApiModelProperty(value = "Delivery address for the order", required = true)
	@NotNull(message = "order_insert.address.not_null")
	@Valid
	private AddressDto address;
}
