package br.com.colatina.fmf.algafood.service.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
	CREATED("Created", List.of()),
	CONFIRMED("Confirmed", List.of(OrderStatusEnum.CREATED)),
	DELIVERED("Delivered", List.of(OrderStatusEnum.CONFIRMED)),
	CANCELED("Canceled", List.of(OrderStatusEnum.CREATED, OrderStatusEnum.CONFIRMED));

	private final String description;
	private final List<OrderStatusEnum> allowedPrevStatuses;

	public boolean canStatusChangeTo(OrderStatusEnum newStatus) {
		return newStatus.getAllowedPrevStatuses().contains(this);
	}
}
