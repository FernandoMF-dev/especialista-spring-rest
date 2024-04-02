package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.model.KitchensXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.service.KitchenCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto;
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
@RequestMapping("/api/kitchens")
public class KitchenController {
	private final KitchenCrudService kitchenCrudService;

	@GetMapping()
	public ResponseEntity<List<KitchenDto>> findAll() {
		log.debug("REST request to find all kitchens");
		return new ResponseEntity<>(kitchenCrudService.findAll(), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<KitchensXmlWrapper> findAllXml() {
		log.debug("REST request to find all kitchens with the response on the XML format");
		return new ResponseEntity<>(kitchenCrudService.findAllXml(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<KitchenDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the kitchen with ID: {}", id);
		return new ResponseEntity<>(kitchenCrudService.findDtoById(id), HttpStatus.OK);
	}

	@GetMapping("/first")
	public ResponseEntity<KitchenDto> findFirst() {
		log.debug("REST request to find the first kitchen it can");
		return new ResponseEntity<>(kitchenCrudService.findFirst(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<KitchenDto> insert(@Valid @RequestBody KitchenDto dto) {
		log.debug("REST request to insert a new kitchen: {}", dto);
		return new ResponseEntity<>(kitchenCrudService.insert(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<KitchenDto> update(@Valid @RequestBody KitchenDto dto, @PathVariable Long id) {
		log.debug("REST request to update kitchen with id {}: {}", id, dto);
		return new ResponseEntity<>(kitchenCrudService.update(dto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete kitchen with id {}", id);
		kitchenCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
