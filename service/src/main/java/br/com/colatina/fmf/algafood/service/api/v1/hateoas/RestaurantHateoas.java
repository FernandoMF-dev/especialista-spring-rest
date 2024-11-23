package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantProductController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantResponsibleController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.GenericObjectDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestaurantHateoas extends EntityHateoas<RestaurantDto> {
	private final AppUserHateoas appUserHateoas;

	public RestaurantHateoas(AddressHateoas addressHateoas, AppUserHateoas appUserHateoas, CuisineHateoas cuisineHateoas,
							 PaymentMethodHateoas paymentMethodHateoas, ProductHateoas productHateoas) {
		super(RestaurantDto.class, addressHateoas, appUserHateoas, cuisineHateoas, paymentMethodHateoas, productHateoas);
		this.appUserHateoas = appUserHateoas;
	}

	@Override
	protected void addModelHypermediaLinks(RestaurantDto model) {
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(getPageLink());
		model.add(linkTo(methodOn(RestaurantProductController.class).findAll(model.getId())).withRel("products"));
		model.add(linkTo(methodOn(RestaurantResponsibleController.class).findAll(model.getId())).withRel("responsibles"));

		addStatusChangeHypermediaLinks(model);
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<RestaurantDto> collection) {
		collection.add(linkTo(methodOn(RestaurantController.class).findAll()).withSelfRel());
		collection.add(getPageLink());
		collection.add(linkTo(methodOn(RestaurantController.class).findFirst()).withRel(IanaLinkRelations.FIRST));
	}

	@Override
	public void mapGenericModel(GenericObjectDto model) {
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	public CollectionModel<AppUserDto> mapResponsiblesCollectionModel(Iterable<AppUserDto> users, Long restaurantId) {
		CollectionModel<AppUserDto> collection = appUserHateoas.mapCollectionModel(users);
		collection.removeLinks();
		collection.add(linkTo(methodOn(RestaurantResponsibleController.class).findAll(restaurantId)).withSelfRel());
		collection.add(linkTo(methodOn(RestaurantResponsibleController.class).associate(restaurantId, null)).withRel("associate"));
		collection.getContent().forEach(responsible -> responsible.add(linkTo(methodOn(RestaurantResponsibleController.class).disassociate(restaurantId, responsible.getId())).withRel("disassociate")));
		return collection;
	}

	private Link getPageLink() {
		Link link = this.getPageLink(linkTo(RestaurantController.class).slash("page").toUri());

		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("name", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("minFreightFee", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("maxFreightFee", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("active", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("cuisineId", TemplateVariable.VariableType.REQUEST_PARAM)
		);

		return Link.of(link.getTemplate().with(filterVariables), link.getRel());
	}

	private void addStatusChangeHypermediaLinks(RestaurantDto model) {
		String toggleOpenRel = Boolean.TRUE.equals(model.getOpen()) ? "close" : "open";
		model.add(linkTo(methodOn(RestaurantController.class).toggleOpen(model.getId(), !model.getOpen())).withRel(toggleOpenRel));

		String toggleActiveRel = Boolean.TRUE.equals(model.getActive()) ? "deactivate" : "activate";
		model.add(linkTo(methodOn(RestaurantController.class).toggleActive(model.getId(), !model.getActive())).withRel(toggleActiveRel));
	}
}
