package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
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

@Relation(collectionRelation = "orders")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> implements Serializable {
	@EqualsAndHashCode.Include
	private String code;

	private Double totalValue;

	private Double subtotal;

	private Double freightFee = 0.0;

	private OffsetDateTime registrationDate;

	private OffsetDateTime confirmationDate;

	private OffsetDateTime deliveryDate;

	private OffsetDateTime cancellationDate;

	private OrderStatusEnum status;

	private GenericObjectDto customer;

	private GenericObjectDto restaurant;

	private GenericObjectDto paymentMethod;

	private List<OrderProductDto> orderProducts = new ArrayList<>();

	private AddressDto address;
}
