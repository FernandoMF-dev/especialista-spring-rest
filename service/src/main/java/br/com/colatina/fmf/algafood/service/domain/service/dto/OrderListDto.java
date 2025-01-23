package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Schema(name = "Model <Order (Listed)>", description = "Representation model for a order when displayed in a list")
@Relation(collectionRelation = "orders")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class OrderListDto extends RepresentationModel<OrderListDto> implements Serializable {
	@Schema(description = "Unique code of the order", example = "123e4567-e89b-12d3-a456-426614174000")
	@EqualsAndHashCode.Include
	private String code;

	@Schema(description = "Total value of the order, including the subtotal and the freight fee", example = "100.00")
	@SortableField(translation = "totalValue")
	private Double value;

	@Schema(description = "Current status of the order", example = "CONFIRMED")
	@SortableField
	private OrderStatusEnum status;

	@Schema(description = "Timestamp of when the order was emitted", example = "2023-10-01T10:00:00Z")
	@SortableField
	private OffsetDateTime registrationDate;

	@Schema(description = "Customer who emitted the order")
	@SortableField(recursive = true)
	private GenericObjectDto customer;

	@Schema(description = "Restaurant that received the order")
	@SortableField(recursive = true)
	private GenericObjectDto restaurant;

	public OrderListDto(String code, Double value, OrderStatusEnum status, OffsetDateTime registrationDate,
						Long customerId, String customerName, Long restaurantId, String restaurantName) {
		this.code = code;
		this.value = value;
		this.status = status;
		this.registrationDate = registrationDate;
		this.customer = new GenericObjectDto(customerId, customerName);
		this.restaurant = new GenericObjectDto(restaurantId, restaurantName);
	}
}
