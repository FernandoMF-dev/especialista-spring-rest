package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.CuisineControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.hateoas.CuisineHateoas;
import br.com.colatina.fmf.algafood.service.api.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
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
@RequestMapping(path = "/api/cuisines", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuisineController implements CuisineControllerDocumentation {
	private final CuisineCrudService cuisineCrudService;
	private final CuisineHateoas cuisineHateoas;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CuisineDto> findAll() {
		log.debug("REST request to find all cuisines");
		List<CuisineDto> cuisines = cuisineCrudService.findAll();
		return cuisineHateoas.mapCollectionModel(cuisines);
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<CuisinesXmlWrapper> findAllXml() {
		log.debug("REST request to find all cuisines with the response on the XML format");
		return new ResponseEntity<>(cuisineCrudService.findAllXml(), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<CuisineDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the cuisine with ID: {}", id);
		CuisineDto cuisine = cuisineCrudService.findDtoById(id);
		cuisineHateoas.mapModel(cuisine);
		return new ResponseEntity<>(cuisine, HttpStatus.OK);
	}

	@Override
	@GetMapping("/first")
	public ResponseEntity<CuisineDto> findFirst() {
		log.debug("REST request to find the first cuisine it can");
		CuisineDto cuisine = cuisineCrudService.findFirst();
		cuisineHateoas.mapModel(cuisine);
		return new ResponseEntity<>(cuisine, HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<CuisineDto> insert(@Valid @RequestBody CuisineDto dto) {
		log.debug("REST request to insert a new cuisine: {}", dto);
		CuisineDto cuisine = cuisineCrudService.insert(dto);
		cuisineHateoas.mapModel(cuisine);
		return new ResponseEntity<>(cuisine, HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<CuisineDto> update(@PathVariable Long id, @Valid @RequestBody CuisineDto dto) {
		log.debug("REST request to update cuisine with id {}: {}", id, dto);
		CuisineDto cuisine = cuisineCrudService.update(dto, id);
		cuisineHateoas.mapModel(cuisine);
		return new ResponseEntity<>(cuisine, HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete cuisine with id {}", id);
		cuisineCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
