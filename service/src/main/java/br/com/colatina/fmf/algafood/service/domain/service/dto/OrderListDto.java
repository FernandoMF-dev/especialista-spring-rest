package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "orders")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class OrderListDto extends RepresentationModel<OrderListDto> implements Serializable {
	@EqualsAndHashCode.Include
	private String code;

	@SortableField(translation = "totalValue")
	private Double value;

	@SortableField
	private OrderStatusEnum status;

	@SortableField
	private OffsetDateTime registrationDate;

	@SortableField(recursive = true)
	private GenericObjectDto customer;

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
