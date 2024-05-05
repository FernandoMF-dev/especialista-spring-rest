package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderListDto implements Serializable {
	private Long id;
	private Double totalValue;
	private OrderStatusEnum status;
	private OffsetDateTime registrationDate;
	private GenericObjectDto user;
	private GenericObjectDto restaurant;

	public OrderListDto(Long id, Double totalValue, OrderStatusEnum status, OffsetDateTime registrationDate,
						Long userId, String userName, Long restaurantId, String restaurantName) {
		this.id = id;
		this.totalValue = totalValue;
		this.status = status;
		this.registrationDate = registrationDate;
		this.user = new GenericObjectDto(userId, userName);
		this.restaurant = new GenericObjectDto(restaurantId, restaurantName);
	}
}
