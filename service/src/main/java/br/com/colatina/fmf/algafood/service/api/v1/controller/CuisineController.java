package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.CuisineControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.CuisineHateoas;
import br.com.colatina.fmf.algafood.service.api.v1.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
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

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/cuisines", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuisineController implements CuisineControllerDocumentation {
	private final CuisineCrudService cuisineCrudService;
	private final CuisineHateoas cuisineHateoas;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@CheckSecurity.Cuisine.Read
	public ResponseEntity<CollectionModel<CuisineDto>> findAll() {
		log.debug("REST request to find all cuisines");
		List<CuisineDto> cuisines = cuisineCrudService.findAll();
		return new ResponseEntity<>(cuisineHateoas.mapCollectionModel(cuisines), HttpStatus.OK);
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	@CheckSecurity.Cuisine.Read
	public ResponseEntity<CuisinesXmlWrapper> findAllXml() {
		log.debug("REST request to find all cuisines with the response on the XML format");
		return new ResponseEntity<>(cuisineCrudService.findAllXml(), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@CheckSecurity.Cuisine.Read
	public ResponseEntity<CuisineDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the cuisine with ID: {}", id);
		CuisineDto cuisine = cuisineCrudService.findDtoById(id);
		return new ResponseEntity<>(cuisineHateoas.mapModel(cuisine), HttpStatus.OK);
	}

	@Override
	@GetMapping("/first")
	@CheckSecurity.Cuisine.Read
	public ResponseEntity<CuisineDto> findFirst() {
		log.debug("REST request to find the first cuisine it can");
		CuisineDto cuisine = cuisineCrudService.findFirst();
		return new ResponseEntity<>(cuisineHateoas.mapModel(cuisine), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	@CheckSecurity.Cuisine.Create
	public ResponseEntity<CuisineDto> insert(@Valid @RequestBody CuisineDto dto) {
		log.debug("REST request to insert a new cuisine: {}", dto);
		CuisineDto cuisine = cuisineCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(cuisine.getId());
		return new ResponseEntity<>(cuisineHateoas.mapModel(cuisine), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@CheckSecurity.Cuisine.Update
	public ResponseEntity<CuisineDto> update(@PathVariable Long id, @Valid @RequestBody CuisineDto dto) {
		log.debug("REST request to update cuisine with id {}: {}", id, dto);
		CuisineDto cuisine = cuisineCrudService.update(dto, id);
		return new ResponseEntity<>(cuisineHateoas.mapModel(cuisine), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@CheckSecurity.Cuisine.Delete
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete cuisine with id {}", id);
		cuisineCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
