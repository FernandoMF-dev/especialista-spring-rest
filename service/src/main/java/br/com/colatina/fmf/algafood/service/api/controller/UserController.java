package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
import br.com.colatina.fmf.algafood.service.domain.service.UserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
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
@RequestMapping("/api/users")
public class UserController {
	private final UserCrudService userCrudService;

	@GetMapping()
	public ResponseEntity<List<UserDto>> findAll() {
		log.debug("REST request to find all users");

		try {
			return new ResponseEntity<>(userCrudService.findAll(), HttpStatus.OK);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the user with ID: {}", id);

		try {
			UserDto state = userCrudService.findDtoById(id);
			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PostMapping()
	public ResponseEntity<UserDto> insert(@Valid @RequestBody UserDto dto) {
		log.debug("REST request to insert a new user: {}", dto);

		try {
			return new ResponseEntity<>(userCrudService.insert(dto), HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto dto, @PathVariable Long id) {
		log.debug("REST request to update user with id {}: {}", id, dto);

		try {
			dto = userCrudService.update(dto, id);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete user with id {}", id);

		try {
			userCrudService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
