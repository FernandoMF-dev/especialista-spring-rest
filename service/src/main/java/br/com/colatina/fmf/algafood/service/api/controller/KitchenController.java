package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.model.KitchensXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kitchens")
public class KitchenController {
	private final KitchenRepository kitchenRepository;

	@GetMapping()
	public ResponseEntity<List<Kitchen>> findAll() {
		log.debug("REST request to find all Kitchens");
		return new ResponseEntity<>(kitchenRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<KitchensXmlWrapper> findAllXml() {
		log.debug("REST request to find all Kitchens with the response on the XML format");
		return new ResponseEntity<>(new KitchensXmlWrapper(kitchenRepository.findAll()), HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<Kitchen> findById(@PathVariable Long id) {
		log.debug("REST request to find the Kitchen with ID: {}", id);
		Optional<Kitchen> kitchen = kitchenRepository.findByIdAndExcludedIsFalse(id);
		return kitchen.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping()
	public ResponseEntity<Kitchen> insert(@Valid @RequestBody Kitchen entity) {
		log.debug("REST request to insert a new kitchen: {}", entity);
		return new ResponseEntity<>(kitchenRepository.save(entity), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Kitchen> update(@Valid @RequestBody Kitchen entity, @PathVariable Long id) {
		log.debug("REST request to update kitchen with id {}: {}", id, entity);

		Optional<Kitchen> saved = kitchenRepository.findByIdAndExcludedIsFalse(id);
		if (saved.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(entity, saved.get(), "id");

		return new ResponseEntity<>(kitchenRepository.save(saved.get()), HttpStatus.OK);
	}

}
