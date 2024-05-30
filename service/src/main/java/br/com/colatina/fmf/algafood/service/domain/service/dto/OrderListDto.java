package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.pageable.SortableField;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderListDto implements Serializable {
	@SortableField
	private String code;
	@SortableField("totalValue")
	private Double value;
	@SortableField
	private OrderStatusEnum status;
	@SortableField
	private OffsetDateTime registrationDate;
	@SortableField
	private GenericObjectDto user;
	@SortableField
	private GenericObjectDto restaurant;

	public OrderListDto(String code, Double value, OrderStatusEnum status, OffsetDateTime registrationDate,
						Long userId, String userName, Long restaurantId, String restaurantName) {
		this.code = code;
		this.value = value;
		this.status = status;
		this.registrationDate = registrationDate;
		this.user = new GenericObjectDto(userId, userName);
		this.restaurant = new GenericObjectDto(restaurantId, restaurantName);
	}
}
