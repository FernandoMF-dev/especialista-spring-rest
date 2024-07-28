package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Order", description = "Representation model for an registered order")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
	@ApiModelProperty(value = "Unique code of the order", example = "123e4567-e89b-12d3-a456-426614174000")
	private String code;

	@ApiModelProperty(value = "Total value of the order, including the subtotal and the freight fee", example = "100.00")
	private Double totalValue;

	@ApiModelProperty(value = "Subtotal of the order", example = "90.00")
	private Double subtotal;

	@ApiModelProperty(value = "Freight fee of the order", example = "10.00")
	private Double freightFee = 0.0;

	@ApiModelProperty(value = "Timestamp of when the order was emitted", example = "2023-10-01T10:00:00Z")
	private OffsetDateTime registrationDate;

	@ApiModelProperty(value = "Timestamp of when the order was accepted by the restaurant", example = "2023-10-01T11:00:00Z")
	private OffsetDateTime confirmationDate;

	@ApiModelProperty(value = "Timestamp of when the order was delivered", example = "2023-10-01T12:00:00Z")
	private OffsetDateTime deliveryDate;

	@ApiModelProperty(value = "Timestamp of when the order was cancelled by the client or the restaurant", example = "2023-10-01T13:00:00Z")
	private OffsetDateTime cancellationDate;

	@ApiModelProperty(value = "Current status of the order", example = "CONFIRMED")
	private OrderStatusEnum status;

	@ApiModelProperty(value = "Customer who emitted the order")
	private GenericObjectDto customer;

	@ApiModelProperty(value = "Restaurant that received the order")
	private GenericObjectDto restaurant;

	@ApiModelProperty(value = "Payment method selected by the client to pay for the order")
	private GenericObjectDto paymentMethod;

	@ApiModelProperty(value = "List of products in the order")
	private List<OrderProductDto> orderProducts = new ArrayList<>();

	@ApiModelProperty(value = "Delivery address for the order delivery")
	private AddressDto address;
}
