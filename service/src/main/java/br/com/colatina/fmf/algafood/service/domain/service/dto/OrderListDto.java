package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderListDto implements Serializable {
	private Long id;
	private Double totalValue;
	private OrderStatusEnum status;
	private GenericObjectDto user;
	private GenericObjectDto restaurant;
	private GenericObjectDto paymentMethod;
	private List<OrderProductListDto> products = new ArrayList<>();

	public OrderListDto(Long id, Double totalValue, OrderStatusEnum status, User user, Restaurant restaurant, PaymentMethod paymentMethod) {
		this.id = id;
		this.totalValue = totalValue;
		this.status = status;
		this.user = new GenericObjectDto(user.getId(), user.getName());
		this.restaurant = new GenericObjectDto(restaurant.getId(), restaurant.getName());
		this.paymentMethod = new GenericObjectDto(paymentMethod.getId(), paymentMethod.getDescription());
	}
}
