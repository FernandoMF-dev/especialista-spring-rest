package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.OrderController;
import br.com.colatina.fmf.algafood.service.api.controller.RestaurantProductController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoas extends EntityHateoas<OrderDto> {
	private final RestaurantHateoas restaurantHateoas;
	private final UserHateoas userHateoas;
	private final PaymentMethodHateoas paymentMethodHateoas;

	public OrderHateoas(RestaurantHateoas restaurantHateoas, UserHateoas userHateoas, PaymentMethodHateoas paymentMethodHateoas) {
		super(OrderDto.class);
		this.restaurantHateoas = restaurantHateoas;
		this.userHateoas = userHateoas;
		this.paymentMethodHateoas = paymentMethodHateoas;
	}

	@Override
	protected void addModelHypermediaLinks(OrderDto model) {
		model.add(linkTo(methodOn(OrderController.class).findByUuid(model.getCode())).withSelfRel());
		model.add(linkTo(methodOn(OrderController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(getPageLink(linkTo(OrderController.class).slash("page").toUri()));

		this.restaurantHateoas.mapModel(model.getRestaurant());
		this.userHateoas.mapModel(model.getCustomer());
		this.paymentMethodHateoas.mapModel(model.getPaymentMethod());

		model.getOrderProducts().forEach(orderProduct -> {
			var link = linkTo(methodOn(RestaurantProductController.class).findById(model.getRestaurant().getId(), orderProduct.getProductId()));
			orderProduct.add(link.withRel("product"));
		});
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<OrderDto> collection) {
		collection.add(linkTo(methodOn(OrderController.class).findAll()).withSelfRel());
		collection.add(getPageLink(linkTo(OrderController.class).slash("page").toUri()));
	}
}
