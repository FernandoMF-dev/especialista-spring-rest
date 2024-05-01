package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.UserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/users")
public class UserController {
	private final UserCrudService userCrudService;

	@GetMapping()
	public ResponseEntity<List<UserDto>> findAll() {
		log.debug("REST request to find all users");
		return new ResponseEntity<>(userCrudService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the user with ID: {}", id);
		return new ResponseEntity<>(userCrudService.findDtoById(id), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<UserDto> insert(@Valid @RequestBody UserInsertDto dto) {
		log.debug("REST request to insert a new user: {}", dto);
		return new ResponseEntity<>(userCrudService.insert(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto dto, @PathVariable Long id) {
		log.debug("REST request to update user with id {}: {}", id, dto);
		return new ResponseEntity<>(userCrudService.update(dto, id), HttpStatus.OK);
	}

	@PatchMapping("/{id}/password")
	public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeDto dto, @PathVariable Long id) {
		log.debug("REST request to change the password of user with id {}: {}", id, dto);
		userCrudService.changePassword(dto, id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete user with id {}", id);
		userCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
