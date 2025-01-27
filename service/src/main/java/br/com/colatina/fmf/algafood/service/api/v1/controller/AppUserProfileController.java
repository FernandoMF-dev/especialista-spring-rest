package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.AppUserProfileControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.AppUserHateoas;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.AppUserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/users/{userId}/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserProfileController implements AppUserProfileControllerDocumentation {
	private final AppUserCrudService appUserCrudService;
	private final AppUserHateoas appUserHateoas;

	@Override
	@GetMapping()
	@CheckSecurity.Profile.Read
	public ResponseEntity<CollectionModel<ProfileDto>> findAll(@PathVariable Long userId) {
		log.debug("REST request to find all profiles associated with the user {}", userId);
		Set<ProfileDto> permissions = appUserCrudService.findAllProfilesByUser(userId);
		return new ResponseEntity<>(appUserHateoas.mapProfilesCollectionModel(permissions, userId), HttpStatus.OK);
	}

	@Override
	@PutMapping("/{profileId}")
	@CheckSecurity.Profile.AssociateUser
	public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long profileId) {
		log.debug("REST request to associate the profile {} with the user {}", profileId, userId);
		appUserCrudService.addProfileToUser(userId, profileId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{profileId}")
	@CheckSecurity.Profile.DisassociateUser
	public ResponseEntity<Void> disassociate(@PathVariable Long userId, @PathVariable Long profileId) {
		log.debug("REST request to disassociate the profile {} from the user {}", profileId, userId);
		appUserCrudService.removeProfileFromUser(userId, profileId);
		return ResponseEntity.noContent().build();
	}
}
