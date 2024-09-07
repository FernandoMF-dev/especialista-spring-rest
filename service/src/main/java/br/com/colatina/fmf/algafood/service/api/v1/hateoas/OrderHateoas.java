package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.OrderController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.OrderFlowController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantProductController;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
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
		model.add(getPageLink());
		addOrderFlowHypermediaLinks(model);

		this.restaurantHateoas.mapGenericModel(model.getRestaurant());
		this.userHateoas.mapGenericModel(model.getCustomer());
		this.paymentMethodHateoas.mapGenericModel(model.getPaymentMethod());

		model.getOrderProducts().forEach(orderProduct -> {
			var link = linkTo(methodOn(RestaurantProductController.class).findById(model.getRestaurant().getId(), orderProduct.getProductId()));
			orderProduct.add(link.withRel("product"));
		});
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<OrderDto> collection) {
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

	private void addOrderFlowHypermediaLinks(OrderDto model) {
		if (model.getStatus().canStatusChangeTo(OrderStatusEnum.CONFIRMED)) {
			model.add(linkTo(methodOn(OrderFlowController.class).confirm(model.getCode())).withRel("confirm"));
		}

		if (model.getStatus().canStatusChangeTo(OrderStatusEnum.CANCELED)) {
			model.add(linkTo(methodOn(OrderFlowController.class).cancel(model.getCode())).withRel("cancel"));
		}

		if (model.getStatus().canStatusChangeTo(OrderStatusEnum.DELIVERED)) {
			model.add(linkTo(methodOn(OrderFlowController.class).deliver(model.getCode())).withRel("deliver"));
		}
	}
}
