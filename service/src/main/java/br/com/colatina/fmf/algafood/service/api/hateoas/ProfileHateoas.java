package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.ProfileController;
import br.com.colatina.fmf.algafood.service.api.controller.ProfilePermissionController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfileHateoas extends EntityHateoas<ProfileDto> {
	public ProfileHateoas() {
		super(ProfileDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(ProfileDto model) {
		model.add(linkTo(methodOn(ProfileController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(ProfileController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(linkTo(methodOn(ProfilePermissionController.class).findAll(model.getId())).withRel("permissions"));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<ProfileDto> collection) {
		collection.add(linkTo(methodOn(ProfileController.class).findAll()).withSelfRel());
	}
}
