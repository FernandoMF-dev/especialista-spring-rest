package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.CityControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.domain.service.CityCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.IanaLinkRelations;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerDocumentation {
	private final CityCrudService cityCrudService;

	@Override
	@GetMapping()
	public ResponseEntity<List<CityDto>> findAll() {
		log.debug("REST request to find all cities");
		return new ResponseEntity<>(cityCrudService.findAll(), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<CityDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the city with ID: {}", id);
		CityDto city = cityCrudService.findDtoById(id);

		city.add(linkTo(methodOn(CityController.class).findById(city.getId())).withSelfRel());
		city.add(linkTo(methodOn(CityController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
		city.getState().add(linkTo(methodOn(StateController.class).findById(city.getState().getId())).withSelfRel());

		return new ResponseEntity<>(city, HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<CityDto> insert(@Valid @RequestBody CityDto dto) {
		log.debug("REST request to insert a new city: {}", dto);
		CityDto result = cityCrudService.insert(dto);
		ResourceUriUtils.addUriInResponseHeader(result.getId());
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<CityDto> update(@PathVariable Long id, @Valid @RequestBody CityDto dto) {
		log.debug("REST request to update city with id {}: {}", id, dto);
		return new ResponseEntity<>(cityCrudService.update(dto, id), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete city with id {}", id);
		cityCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
