package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "Model <Order>", description = "Representation model for an registered order")
@Relation(collectionRelation = "orders")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> implements Serializable {
	@Schema(description = "Unique code of the order", example = "123e4567-e89b-12d3-a456-426614174000")
	@EqualsAndHashCode.Include
	private String code;

	@Schema(description = "Total value of the order, including the subtotal and the freight fee", example = "100.00")
	private Double totalValue;

	@Schema(description = "Subtotal of the order", example = "90.00")
	private Double subtotal;

	@Schema(description = "Freight fee of the order", example = "10.00")
	private Double freightFee = 0.0;

	@Schema(description = "Timestamp of when the order was emitted", example = "2023-10-01T10:00:00Z")
	private OffsetDateTime registrationDate;

	@Schema(description = "Timestamp of when the order was accepted by the restaurant", example = "2023-10-01T11:00:00Z")
	private OffsetDateTime confirmationDate;

	@Schema(description = "Timestamp of when the order was delivered", example = "2023-10-01T12:00:00Z")
	private OffsetDateTime deliveryDate;

	@Schema(description = "Timestamp of when the order was cancelled by the client or the restaurant", example = "2023-10-01T13:00:00Z")
	private OffsetDateTime cancellationDate;

	@Schema(description = "Current status of the order", example = "CONFIRMED")
	private OrderStatusEnum status;

	@Schema(description = "Customer who emitted the order")
	private GenericObjectDto customer;

	@Schema(description = "Restaurant that received the order")
	private GenericObjectDto restaurant;

	@Schema(description = "Payment method selected by the client to pay for the order")
	private GenericObjectDto paymentMethod;

	@Schema(description = "List of products in the order")
	private List<OrderProductDto> orderProducts = new ArrayList<>();

	@Schema(description = "Delivery address for the order delivery")
	private AddressDto address;
}
