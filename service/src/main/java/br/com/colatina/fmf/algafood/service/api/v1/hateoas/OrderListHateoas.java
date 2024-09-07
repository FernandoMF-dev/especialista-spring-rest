package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.OrderController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
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
		model.add(getPageLink());

		this.restaurantHateoas.mapGenericModel(model.getRestaurant());
		this.userHateoas.mapGenericModel(model.getCustomer());
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<OrderListDto> collection) {
		collection.add(linkTo(methodOn(OrderController.class).findAll()).withSelfRel());
		collection.add(getPageLink());
	}

	private Link getPageLink() {
		Link link = this.getPageLink(linkTo(OrderController.class).slash("page").toUri());

		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("status", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("customerId", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("minRegistrationDate", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("maxRegistrationDate", TemplateVariable.VariableType.REQUEST_PARAM)
		);

		return Link.of(link.getTemplate().with(filterVariables), link.getRel());
	}
}
