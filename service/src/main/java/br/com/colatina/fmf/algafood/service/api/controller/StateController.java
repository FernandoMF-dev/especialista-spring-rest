package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRule;
import br.com.colatina.fmf.algafood.service.domain.service.StateCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
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
@RequestMapping("/api/states")
public class StateController {
	private final StateCrudService stateCrudService;

	@GetMapping()
	public ResponseEntity<List<StateDto>> findAll() {
		log.debug("REST request to find all states");

		try {
			return new ResponseEntity<>(stateCrudService.findAll(), HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<StateDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the state with ID: {}", id);

		try {
			StateDto state = stateCrudService.findDtoById(id);
			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PostMapping()
	public ResponseEntity<StateDto> insert(@Valid @RequestBody StateDto dto) {
		log.debug("REST request to insert a new state: {}", dto);

		try {
			return new ResponseEntity<>(stateCrudService.insert(dto), HttpStatus.CREATED);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<StateDto> update(@Valid @RequestBody StateDto dto, @PathVariable Long id) {
		log.debug("REST request to update state with id {}: {}", id, dto);

		try {
			dto = stateCrudService.update(dto, id);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete state with id {}", id);

		try {
			stateCrudService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
