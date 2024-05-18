package br.com.colatina.fmf.algafood.service.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
	CREATED("Created"),
	CONFIRMED("Confirmed"),
	DELIVERED("Delivered"),
	CANCELED("Canceled");

	private final String description;
}
