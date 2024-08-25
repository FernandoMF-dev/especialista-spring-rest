package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.OrderController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderListHateoas extends EntityHateoas<OrderListDto> {
	private final RestaurantHateoas restaurantHateoas;
	private final UserHateoas userHateoas;

	public OrderListHateoas(RestaurantHateoas restaurantHateoas, UserHateoas userHateoas) {
		super(OrderListDto.class);
		this.restaurantHateoas = restaurantHateoas;
		this.userHateoas = userHateoas;
	}

	@Override
	protected void addModelHypermediaLinks(OrderListDto model) {
		model.add(linkTo(methodOn(OrderController.class).findByUuid(model.getCode())).withSelfRel());
		model.add(linkTo(methodOn(OrderController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(getPageLink(linkTo(OrderController.class).slash("page").toUri()));

		this.restaurantHateoas.mapGenericModel(model.getRestaurant());
		this.userHateoas.mapGenericModel(model.getCustomer());
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<OrderListDto> collection) {
		collection.add(linkTo(methodOn(OrderController.class).findAll()).withSelfRel());
		collection.add(getPageLink(linkTo(OrderController.class).slash("page").toUri()));
	}
}
