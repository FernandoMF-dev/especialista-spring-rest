package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.UserController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.UserProfileController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.GenericObjectDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoas extends EntityHateoas<UserDto> {
	private final ProfileHateoas profileHateoas;

	public UserHateoas(ProfileHateoas profileHateoas) {
		super(UserDto.class, profileHateoas);
		this.profileHateoas = profileHateoas;
	}

	@Override
	protected void addModelHypermediaLinks(UserDto model) {
		model.add(linkTo(methodOn(UserController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(UserController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(linkTo(methodOn(UserProfileController.class).findAll(model.getId())).withRel("profiles"));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<UserDto> collection) {
		collection.add(linkTo(methodOn(UserController.class).findAll()).withSelfRel());
	}

	@Override
	public void mapGenericModel(GenericObjectDto model) {
		model.add(linkTo(methodOn(UserController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(UserController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	public CollectionModel<ProfileDto> mapProfilesCollectionModel(Iterable<ProfileDto> profiles, Long userId) {
		CollectionModel<ProfileDto> collection = profileHateoas.mapCollectionModel(profiles);
		collection.removeLinks();
		collection.add(linkTo(methodOn(UserProfileController.class).findAll(userId)).withSelfRel());
		collection.add(linkTo(methodOn(UserProfileController.class).associate(userId, null)).withRel("associate"));
		collection.getContent().forEach(profile -> profile.add(linkTo(methodOn(UserProfileController.class).disassociate(userId, profile.getId())).withRel("disassociate")));
		return collection;
	}
}
