package br.com.colatina.fmf.algafood.service.api.v2.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v2.assembler.CityModelAssemblerV2;
import br.com.colatina.fmf.algafood.service.api.v2.assembler.CityModelDisassemblerV2;
import br.com.colatina.fmf.algafood.service.api.v2.model.CityModelV2;
import br.com.colatina.fmf.algafood.service.api.v2.model.input.CityInputV2;
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
//@RequestMapping(path = "/api/cities", produces = CustomMediaTypes.V2_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/api/v2/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityControllerV2 {
	private final CityCrudService cityCrudService;

	private final CityModelAssemblerV2 cityModelAssemblerV2;
	private final CityModelDisassemblerV2 cityModelDisassemblerV2;

	@GetMapping()
	public CollectionModel<CityModelV2> findAll() {
		log.debug("REST V2 request to find all cities");
		List<CityDto> cities = cityCrudService.findAll();
		return cityModelAssemblerV2.toCollectionModel(cities);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CityModelV2> findById(@PathVariable Long id) {
		log.debug("REST V2 request to find the city with ID: {}", id);
		CityDto dto = cityCrudService.findDtoById(id);
		return new ResponseEntity<>(cityModelAssemblerV2.toModel(dto), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<CityModelV2> insert(@Valid @RequestBody CityInputV2 input) {
		log.debug("REST V2 request to insert a new city: {}", input);
		CityDto dto = cityModelDisassemblerV2.toDomainObject(input);
		dto = cityCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(dto.getId());
		return new ResponseEntity<>(cityModelAssemblerV2.toModel(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CityModelV2> update(@PathVariable Long id, @Valid @RequestBody CityInputV2 input) {
		log.debug("REST V2 request to update city with id {}: {}", id, input);
		CityDto dto = cityModelDisassemblerV2.toDomainObject(input);
		dto = cityCrudService.update(dto, id);
		return new ResponseEntity<>(cityModelAssemblerV2.toModel(dto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST V2 request to delete city with id {}", id);
		cityCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
