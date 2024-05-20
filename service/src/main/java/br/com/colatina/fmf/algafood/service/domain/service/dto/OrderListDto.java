package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderListDto implements Serializable {
	private String code;
	private Double value;
	private OrderStatusEnum status;
	private OffsetDateTime registrationDate;
	private GenericObjectDto user;
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
