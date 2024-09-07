package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestaurantListHateoas extends EntityHateoas<RestaurantListDto> {
	public RestaurantListHateoas() {
		super(RestaurantListDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(RestaurantListDto model) {
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(getPageLink());
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<RestaurantListDto> collection) {
		collection.add(linkTo(methodOn(RestaurantController.class).findAll()).withSelfRel());
		collection.add(getPageLink());
		collection.add(linkTo(methodOn(RestaurantController.class).findFirst()).withRel(IanaLinkRelations.FIRST));
	}

	private Link getPageLink() {
		Link link = this.getPageLink(linkTo(RestaurantController.class).slash("page").toUri());

		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("name", VariableType.REQUEST_PARAM),
				new TemplateVariable("minFreightFee", VariableType.REQUEST_PARAM),
				new TemplateVariable("maxFreightFee", VariableType.REQUEST_PARAM),
				new TemplateVariable("active", VariableType.REQUEST_PARAM),
				new TemplateVariable("cuisineId", VariableType.REQUEST_PARAM)
		);

		return Link.of(link.getTemplate().with(filterVariables), link.getRel());
	}
}
