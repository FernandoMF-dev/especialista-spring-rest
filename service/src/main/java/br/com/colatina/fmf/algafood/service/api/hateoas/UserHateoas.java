package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.UserController;
import br.com.colatina.fmf.algafood.service.api.controller.UserProfileController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoas extends EntityHateoas<UserDto> {
	public UserHateoas() {
		super(UserDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(UserDto model) {
		model.add(linkTo(methodOn(UserController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(UserController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(linkTo(methodOn(UserProfileController.class).findAll(model.getId())).withRel("user-profiles"));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<UserDto> collection) {
		collection.add(linkTo(methodOn(UserController.class).findAll()).withSelfRel());
	}
}
