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
	public RestaurantHateoas(AddressHateoas addressHateoas, UserHateoas userHateoas, CuisineHateoas cuisineHateoas,
							 PaymentMethodHateoas paymentMethodHateoas) {
		super(RestaurantDto.class, addressHateoas, userHateoas, cuisineHateoas, paymentMethodHateoas);
	}

	@Override
	protected void addModelHypermediaLinks(RestaurantDto model) {
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<RestaurantDto> collection) {
		collection.add(linkTo(methodOn(RestaurantController.class).findAll()).withSelfRel());
		collection.add(linkTo(RestaurantController.class).slash("page").withRel("page"));
		collection.add(linkTo(methodOn(RestaurantController.class).findFirst()).withRel(IanaLinkRelations.FIRST));
	}
}
