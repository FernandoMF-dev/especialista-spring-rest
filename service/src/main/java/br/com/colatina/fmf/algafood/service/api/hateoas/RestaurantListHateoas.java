package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
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
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<RestaurantListDto> collection) {
		collection.add(linkTo(methodOn(RestaurantController.class).findAll()).withSelfRel());
		collection.add(linkTo(methodOn(RestaurantController.class).findFirst()).withRel(IanaLinkRelations.FIRST));
	}
}
