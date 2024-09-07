package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.UserControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.UserHateoas;
import br.com.colatina.fmf.algafood.service.domain.service.UserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserInsertDto;
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
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerDocumentation {
	private final UserCrudService userCrudService;
	private final UserHateoas userHateoas;

	@Override
	@GetMapping()
	public CollectionModel<UserDto> findAll() {
		log.debug("REST request to find all users");
		List<UserDto> users = userCrudService.findAll();
		return userHateoas.mapCollectionModel(users);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the user with ID: {}", id);
		UserDto user = userCrudService.findDtoById(id);
		return new ResponseEntity<>(userHateoas.mapModel(user), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<UserDto> insert(@Valid @RequestBody UserInsertDto dto) {
		log.debug("REST request to insert a new user: {}", dto);
		UserDto user = userCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(user.getId());
		return new ResponseEntity<>(userHateoas.mapModel(user), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
		log.debug("REST request to update user with id {}: {}", id, dto);
		UserDto user = userCrudService.update(dto, id);
		return new ResponseEntity<>(userHateoas.mapModel(user), HttpStatus.OK);
	}

	@Override
	@PatchMapping("/{id}/password")
	public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody PasswordChangeDto dto) {
		log.debug("REST request to change the password of user with id {}: {}", id, dto);
		userCrudService.changePassword(dto, id);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete user with id {}", id);
		userCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
