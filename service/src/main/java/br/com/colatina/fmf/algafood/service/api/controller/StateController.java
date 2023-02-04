package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.model.State;
import br.com.colatina.fmf.algafood.service.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/states")
public class StateController {
	private final StateRepository stateRepository;

	@GetMapping()
	public ResponseEntity<List<State>> findAll() {
		log.debug("REST request to find all States");
		return new ResponseEntity<>(stateRepository.findAll(), HttpStatus.OK);
	}
}
