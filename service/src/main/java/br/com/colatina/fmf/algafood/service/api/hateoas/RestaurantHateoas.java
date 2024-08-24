package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestaurantHateoas extends EntityHateoas<RestaurantDto> {
	private final CityHateoas cityHateoas;

	public RestaurantHateoas(CityHateoas cityHateoas, UserHateoas userHateoas) {
		super(RestaurantDto.class, userHateoas);
		this.cityHateoas = cityHateoas;
	}

	@Override
	protected void addModelHypermediaLinks(RestaurantDto model) {
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));

		cityHateoas.addModelHypermediaLinks(model.getAddress().getCity());
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<RestaurantDto> collection) {
		collection.add(linkTo(methodOn(RestaurantController.class).findAll()).withSelfRel());
		collection.add(linkTo(methodOn(RestaurantController.class).findFirst()).withRel(IanaLinkRelations.FIRST));
	}
}
