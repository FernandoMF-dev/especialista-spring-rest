package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.AppUserControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.AppUserHateoas;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.AppUserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController implements AppUserControllerDocumentation {
	private final AppUserCrudService appUserCrudService;
	private final AppUserHateoas appUserHateoas;

	@Override
	@GetMapping()
	@CheckSecurity.AppUser.List
	public CollectionModel<AppUserDto> findAll() {
		log.debug("REST request to find all users");
		List<AppUserDto> users = appUserCrudService.findAll();
		return appUserHateoas.mapCollectionModel(users);
	}

	@Override
	@GetMapping("/{id}")
	@CheckSecurity.AppUser.Read
	public ResponseEntity<AppUserDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the user with ID: {}", id);
		AppUserDto user = appUserCrudService.findDtoById(id);
		return new ResponseEntity<>(appUserHateoas.mapModel(user), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	@CheckSecurity.Public
	public ResponseEntity<AppUserDto> insert(@Valid @RequestBody AppUserInsertDto dto) {
		log.debug("REST request to insert a new user: {}", dto);
		AppUserDto user = appUserCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(user.getId());
		return new ResponseEntity<>(appUserHateoas.mapModel(user), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@CheckSecurity.AppUser.Update
	public ResponseEntity<AppUserDto> update(@PathVariable Long id, @Valid @RequestBody AppUserDto dto) {
		log.debug("REST request to update user with id {}: {}", id, dto);
		AppUserDto user = appUserCrudService.update(dto, id);
		return new ResponseEntity<>(appUserHateoas.mapModel(user), HttpStatus.OK);
	}

	@Override
	@PatchMapping("/{id}/password")
	@CheckSecurity.AppUser.ChangePassword
	public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody PasswordChangeDto dto) {
		log.debug("REST request to change the password of user with id {}: {}", id, dto);
		appUserCrudService.changePassword(dto, id);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{id}")
	@CheckSecurity.AppUser.Delete
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete user with id {}", id);
		appUserCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
