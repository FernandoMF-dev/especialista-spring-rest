package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.CityControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.CityHateoas;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.CityCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
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
//@RequestMapping(path = "/api/cities", produces = CustomMediaTypes.V1_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/api/v1/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerDocumentation {
	private final CityCrudService cityCrudService;
	private final CityHateoas cityHateoas;

	@Override
	@GetMapping()
	@CheckSecurity.City.Read
	public ResponseEntity<CollectionModel<CityDto>> findAll() {
		log.debug("REST V1 request to find all cities");
		List<CityDto> cities = cityCrudService.findAll();
		return new ResponseEntity<>(cityHateoas.mapCollectionModel(cities), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@CheckSecurity.City.Read
	public ResponseEntity<CityDto> findById(@PathVariable Long id) {
		log.debug("REST V1 request to find the city with ID: {}", id);
		CityDto city = cityCrudService.findDtoById(id);
		return new ResponseEntity<>(cityHateoas.mapModel(city), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	@CheckSecurity.City.Create
	public ResponseEntity<CityDto> insert(@Valid @RequestBody CityDto dto) {
		log.debug("REST V1 request to insert a new city: {}", dto);
		CityDto city = cityCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(city.getId());
		return new ResponseEntity<>(cityHateoas.mapModel(city), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@CheckSecurity.City.Update
	public ResponseEntity<CityDto> update(@PathVariable Long id, @Valid @RequestBody CityDto dto) {
		log.debug("REST V1 request to update city with id {}: {}", id, dto);
		CityDto city = cityCrudService.update(dto, id);
		return new ResponseEntity<>(cityHateoas.mapModel(city), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	@CheckSecurity.City.Delete
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST V1 request to delete city with id {}", id);
		cityCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
