package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.StateController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StateHateoas extends EntityHateoas<StateDto> {
	public StateHateoas() {
		super(StateDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(StateDto model) {
		model.add(linkTo(methodOn(StateController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(StateController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<StateDto> collection) {
		collection.add(linkTo(methodOn(StateController.class).findAll()).withSelfRel());
	}
}
