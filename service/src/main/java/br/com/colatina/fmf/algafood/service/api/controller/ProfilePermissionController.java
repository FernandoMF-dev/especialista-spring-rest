package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/profiles/{profileId}/permissions")
public class ProfilePermissionController {
	private final ProfileCrudService profileCrudService;

	@GetMapping()
	public ResponseEntity<Set<PermissionDto>> findAll(@PathVariable Long profileId) {
		log.debug("REST request to find all permissions associated with the profile {}", profileId);
		Set<PermissionDto> permissions = profileCrudService.findAllPermissionsByProfile(profileId);
		return new ResponseEntity<>(permissions, HttpStatus.OK);
	}

	@PutMapping("/{permissionId}")
	public ResponseEntity<Void> associate(@PathVariable Long profileId, @PathVariable Long permissionId) {
		log.debug("REST request to associate the permission {} with the profile {}", permissionId, profileId);
		profileCrudService.addPermissionToProfile(profileId, permissionId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{permissionId}")
	public ResponseEntity<Void> disassociate(@PathVariable Long profileId, @PathVariable Long permissionId) {
		log.debug("REST request to disassociate the permission {} from the profile {}", permissionId, profileId);
		profileCrudService.removePermissionFromProfile(profileId, permissionId);
		return ResponseEntity.noContent().build();
	}
}
