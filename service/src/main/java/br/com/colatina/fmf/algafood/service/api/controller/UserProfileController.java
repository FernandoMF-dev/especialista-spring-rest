package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.UserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
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
@RequestMapping("/api/users/{userId}/profiles")
public class UserProfileController {
	private final UserCrudService userCrudService;

	@GetMapping()
	public ResponseEntity<Set<ProfileDto>> findAll(@PathVariable Long userId) {
		log.debug("REST request to find all profiles associated with the user {}", userId);
		Set<ProfileDto> permissions = userCrudService.findAllPermissionsByProfileId(userId);
		return new ResponseEntity<>(permissions, HttpStatus.OK);
	}

	@PutMapping("/{profileId}")
	public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long profileId) {
		log.debug("REST request to associate the profile {} with the user {}", profileId, userId);
		userCrudService.addPermissionToProfile(userId, profileId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{profileId}")
	public ResponseEntity<Void> disassociate(@PathVariable Long userId, @PathVariable Long profileId) {
		log.debug("REST request to disassociate the profile {} from the user {}", profileId, userId);
		userCrudService.removePermissionFromProfile(userId, profileId);
		return ResponseEntity.noContent().build();
	}
}
