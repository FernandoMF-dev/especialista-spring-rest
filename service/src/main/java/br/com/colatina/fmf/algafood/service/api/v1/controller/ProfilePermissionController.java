package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.ProfilePermissionControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.ProfileHateoas;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
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
@RequestMapping(path = "/api/v1/profiles/{profileId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfilePermissionController implements ProfilePermissionControllerDocumentation {
	private final ProfileCrudService profileCrudService;
	private final ProfileHateoas profileHateoas;

	@Override
	@GetMapping()
	@CheckSecurity.Profile.Read
	public ResponseEntity<CollectionModel<PermissionDto>> findAll(@PathVariable Long profileId) {
		log.debug("REST request to find all permissions associated with the profile {}", profileId);
		Set<PermissionDto> permissions = profileCrudService.findAllPermissionsByProfile(profileId);
		return new ResponseEntity<>(profileHateoas.mapPermissionsCollectionModel(permissions, profileId), HttpStatus.OK);
	}

	@Override
	@PutMapping("/{permissionId}")
	@CheckSecurity.Profile.AssociatePermission
	public ResponseEntity<Void> associate(@PathVariable Long profileId, @PathVariable Long permissionId) {
		log.debug("REST request to associate the permission {} with the profile {}", permissionId, profileId);
		profileCrudService.addPermissionToProfile(profileId, permissionId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{permissionId}")
	@CheckSecurity.Profile.DisassociatePermission
	public ResponseEntity<Void> disassociate(@PathVariable Long profileId, @PathVariable Long permissionId) {
		log.debug("REST request to disassociate the permission {} from the profile {}", permissionId, profileId);
		profileCrudService.removePermissionFromProfile(profileId, permissionId);
		return ResponseEntity.noContent().build();
	}
}
