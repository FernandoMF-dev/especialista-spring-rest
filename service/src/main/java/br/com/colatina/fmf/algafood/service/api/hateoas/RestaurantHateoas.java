package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.api.controller.RestaurantProductController;
import br.com.colatina.fmf.algafood.service.api.controller.RestaurantResponsibleController;
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
	public RestaurantHateoas(AddressHateoas addressHateoas, UserHateoas userHateoas, CuisineHateoas cuisineHateoas,
							 PaymentMethodHateoas paymentMethodHateoas, ProductHateoas productHateoas) {
		super(RestaurantDto.class, addressHateoas, userHateoas, cuisineHateoas, paymentMethodHateoas, productHateoas);
	}

	@Override
	protected void addModelHypermediaLinks(RestaurantDto model) {
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(getPageLink());
		model.add(linkTo(methodOn(RestaurantProductController.class).findAll(model.getId())).withRel("products"));
		model.add(linkTo(methodOn(RestaurantResponsibleController.class).findAll(model.getId())).withRel("responsibles"));
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
}
