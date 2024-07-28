package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.StateControllerDocumentation;
import br.com.colatina.fmf.algafood.service.domain.service.StateCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(path = "/api/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerDocumentation {
	private final StateCrudService stateCrudService;

	@Override
	@GetMapping()
	public ResponseEntity<List<StateDto>> findAll() {
		log.debug("REST request to find all states");
		return new ResponseEntity<>(stateCrudService.findAll(), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<StateDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the state with ID: {}", id);
		return new ResponseEntity<>(stateCrudService.findDtoById(id), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<StateDto> insert(@Valid @RequestBody StateDto dto) {
		log.debug("REST request to insert a new state: {}", dto);
		return new ResponseEntity<>(stateCrudService.insert(dto), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<StateDto> update(@PathVariable Long id, @Valid @RequestBody StateDto dto) {
		log.debug("REST request to update state with id {}: {}", id, dto);
		return new ResponseEntity<>(stateCrudService.update(dto, id), HttpStatus.OK);

	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete state with id {}", id);
		stateCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
