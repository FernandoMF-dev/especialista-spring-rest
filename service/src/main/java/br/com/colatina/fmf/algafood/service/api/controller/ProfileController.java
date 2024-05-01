package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
	private final ProfileCrudService profileCrudService;

	@GetMapping()
	public ResponseEntity<List<ProfileDto>> findAll() {
		log.debug("REST request to find all profiles");
		return new ResponseEntity<>(profileCrudService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfileDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the profile with ID: {}", id);
		ProfileDto profile = profileCrudService.findDtoById(id);
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<ProfileDto> insert(@Valid @RequestBody ProfileDto dto) {
		log.debug("REST request to insert a new profile: {}", dto);
		return new ResponseEntity<>(profileCrudService.insert(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProfileDto> update(@Valid @RequestBody ProfileDto dto, @PathVariable Long id) {
		log.debug("REST request to update profile with id {}: {}", id, dto);
		return new ResponseEntity<>(profileCrudService.update(dto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete profile with id {}", id);
		profileCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
