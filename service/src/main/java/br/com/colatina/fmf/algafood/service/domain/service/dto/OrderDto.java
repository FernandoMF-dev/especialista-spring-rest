package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
	private Long id;
	private Double totalValue;
	private Double subtotal;
	private Double freightFee = 0.0;
	private LocalDateTime registrationDate;
	private LocalDateTime confirmationDate;
	private LocalDateTime deliveryDate;
	private LocalDateTime cancellationDate;
	private OrderStatusEnum status;
	private UserDto user;
	private RestaurantDto restaurant;
	private PaymentMethodDto paymentMethod;
	private List<OrderProductDto> orderProducts = new ArrayList<>();
	private AddressDto address;
}
