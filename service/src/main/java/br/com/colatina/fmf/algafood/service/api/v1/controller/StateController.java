package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.StateControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.StateHateoas;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.StateCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
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
@RequestMapping(path = "/api/v1/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerDocumentation {
	private final StateCrudService stateCrudService;
	private final StateHateoas stateHateoas;

	@Override
	@GetMapping()
	@CheckSecurity.State.Read
	public ResponseEntity<CollectionModel<StateDto>> findAll() {
		log.debug("REST request to find all states");
		List<StateDto> states = stateCrudService.findAll();
		return new ResponseEntity<>(stateHateoas.mapCollectionModel(states), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@CheckSecurity.State.Read
	public ResponseEntity<StateDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the state with ID: {}", id);
		StateDto state = stateCrudService.findDtoById(id);
		return new ResponseEntity<>(stateHateoas.mapModel(state), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	@CheckSecurity.State.Create
	public ResponseEntity<StateDto> insert(@Valid @RequestBody StateDto dto) {
		log.debug("REST request to insert a new state: {}", dto);
		StateDto state = stateCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(state.getId());
		return new ResponseEntity<>(stateHateoas.mapModel(state), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@CheckSecurity.State.Update
	public ResponseEntity<StateDto> update(@PathVariable Long id, @Valid @RequestBody StateDto dto) {
		log.debug("REST request to update state with id {}: {}", id, dto);
		StateDto state = stateCrudService.update(dto, id);
		return new ResponseEntity<>(stateHateoas.mapModel(state), HttpStatus.OK);

	}

	@Override
	@DeleteMapping("/{id}")
	@CheckSecurity.State.Delete
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete state with id {}", id);
		stateCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
