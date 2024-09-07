package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.CuisineController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CuisineHateoas extends EntityHateoas<CuisineDto> {
	public CuisineHateoas() {
		super(CuisineDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(CuisineDto model) {
		model.add(linkTo(methodOn(CuisineController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(CuisineController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<CuisineDto> collection) {
		collection.add(linkTo(methodOn(CuisineController.class).findAll()).withSelfRel());
	}
}
