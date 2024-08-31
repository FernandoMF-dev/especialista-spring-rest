package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.ProfileControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.hateoas.ProfileHateoas;
import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
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
@RequestMapping(path = "/api/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController implements ProfileControllerDocumentation {
	private final ProfileCrudService profileCrudService;
	private final ProfileHateoas profileHateoas;

	@Override
	@GetMapping()
	public ResponseEntity<CollectionModel<ProfileDto>> findAll() {
		log.debug("REST request to find all profiles");
		List<ProfileDto> profiles = profileCrudService.findAll();
		return new ResponseEntity<>(profileHateoas.mapCollectionModel(profiles), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<ProfileDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the profile with ID: {}", id);
		ProfileDto profile = profileCrudService.findDtoById(id);
		return new ResponseEntity<>(profileHateoas.mapModel(profile), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<ProfileDto> insert(@Valid @RequestBody ProfileDto dto) {
		log.debug("REST request to insert a new profile: {}", dto);
		ProfileDto profile = profileCrudService.insert(dto);
		return new ResponseEntity<>(profileHateoas.mapModel(profile), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<ProfileDto> update(@PathVariable Long id, @Valid @RequestBody ProfileDto dto) {
		log.debug("REST request to update profile with id {}: {}", id, dto);
		ProfileDto profile = profileCrudService.update(dto, id);
		return new ResponseEntity<>(profileHateoas.mapModel(profile), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete profile with id {}", id);
		profileCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
