package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.CityController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityHateoas extends EntityHateoas<CityDto> {
	public CityHateoas(StateHateoas stateHateoas) {
		super(CityDto.class, stateHateoas);
	}

	@Override
	protected void addModelHypermediaLinks(CityDto model) {
		model.add(linkTo(methodOn(CityController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(CityController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<CityDto> collection) {
		collection.add(linkTo(methodOn(CityController.class).findAll()).withSelfRel());
	}
}
