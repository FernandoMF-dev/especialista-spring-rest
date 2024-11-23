package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.AppUserController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.AppUserProfileController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.GenericObjectDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserHateoas extends EntityHateoas<AppUserDto> {
	private final ProfileHateoas profileHateoas;

	public AppUserHateoas(ProfileHateoas profileHateoas) {
		super(AppUserDto.class, profileHateoas);
		this.profileHateoas = profileHateoas;
	}

	@Override
	protected void addModelHypermediaLinks(AppUserDto model) {
		model.add(linkTo(methodOn(AppUserController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(AppUserController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		model.add(linkTo(methodOn(AppUserProfileController.class).findAll(model.getId())).withRel("profiles"));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<AppUserDto> collection) {
		collection.add(linkTo(methodOn(AppUserController.class).findAll()).withSelfRel());
	}

	@Override
	public void mapGenericModel(GenericObjectDto model) {
		model.add(linkTo(methodOn(AppUserController.class).findById(model.getId())).withSelfRel());
		model.add(linkTo(methodOn(AppUserController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
	}

	public CollectionModel<ProfileDto> mapProfilesCollectionModel(Iterable<ProfileDto> profiles, Long userId) {
		CollectionModel<ProfileDto> collection = profileHateoas.mapCollectionModel(profiles);
		collection.removeLinks();
		collection.add(linkTo(methodOn(AppUserProfileController.class).findAll(userId)).withSelfRel());
		collection.add(linkTo(methodOn(AppUserProfileController.class).associate(userId, null)).withRel("associate"));
		collection.getContent().forEach(profile -> profile.add(linkTo(methodOn(AppUserProfileController.class).disassociate(userId, profile.getId())).withRel("disassociate")));
		return collection;
	}
}
