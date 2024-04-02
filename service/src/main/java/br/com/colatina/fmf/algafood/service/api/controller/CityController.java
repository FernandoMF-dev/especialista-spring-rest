package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.CityCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/cities")
public class CityController {
	private final CityCrudService cityCrudService;

	@GetMapping()
	public ResponseEntity<List<CityDto>> findAll() {
		log.debug("REST request to find all cities");
		return new ResponseEntity<>(cityCrudService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CityDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the city with ID: {}", id);
		CityDto city = cityCrudService.findDtoById(id);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<CityDto> insert(@Valid @RequestBody CityDto dto) {
		log.debug("REST request to insert a new city: {}", dto);
		return new ResponseEntity<>(cityCrudService.insert(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CityDto> update(@Valid @RequestBody CityDto dto, @PathVariable Long id) {
		log.debug("REST request to update city with id {}: {}", id, dto);
		return new ResponseEntity<>(cityCrudService.update(dto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete city with id {}", id);
		cityCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
