package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
	@NotNull(message = "Must specify the id of the user issuing the order")
	private Long userId;
	@NotNull(message = "Must specify the id of the restaurant to which the order is being issued")
	private Long restaurantId;
	@NotNull(message = "Must specify the id of the payment method chosen for this order")
	private Long paymentMethodId;
	@NotNull(message = "Must specify how many installments the payment for this order is being divided into")
	@Min(value = 1, message = "The number of installments cannot be less than 1")
	private Integer installments;
	@NotEmpty(message = "The order product list cannot be empty")
	@Valid
	private List<OrderProductInsertDto> orderProducts = new ArrayList<>();
	@NotNull(message = "The address cannot be null")
	@Valid
	private AddressDto address;
}
