package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.ProfileController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.ProfilePermissionController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfileHateoas extends EntityHateoas<ProfileDto> {
	private final PermissionHateoas permissionHateoas;

	public ProfileHateoas(PermissionHateoas permissionHateoas) {
		super(ProfileDto.class, permissionHateoas);
		this.permissionHateoas = permissionHateoas;
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

	public CollectionModel<PermissionDto> mapPermissionsCollectionModel(Iterable<PermissionDto> permissions, Long profileId) {
		CollectionModel<PermissionDto> collection = permissionHateoas.mapCollectionModel(permissions);
		collection.removeLinks();
		collection.add(linkTo(methodOn(ProfilePermissionController.class).findAll(profileId)).withSelfRel());
		collection.add(linkTo(methodOn(ProfilePermissionController.class).associate(profileId, null)).withRel("associate"));
		collection.getContent().forEach(permission -> permission.add(linkTo(methodOn(ProfilePermissionController.class).disassociate(profileId, permission.getId())).withRel("disassociate")));
		return collection;
	}
}
