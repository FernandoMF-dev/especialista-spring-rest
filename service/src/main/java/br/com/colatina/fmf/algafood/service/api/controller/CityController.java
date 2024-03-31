package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRuleException;
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
		log.debug("REST request to find all Citys");

		try {
			return new ResponseEntity<>(cityCrudService.findAll(), HttpStatus.OK);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CityDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the City with ID: {}", id);

		try {
			CityDto city = cityCrudService.findDtoById(id);
			return new ResponseEntity<>(city, HttpStatus.OK);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PostMapping()
	public ResponseEntity<CityDto> insert(@Valid @RequestBody CityDto dto) {
		log.debug("REST request to insert a new city: {}", dto);

		try {
			return new ResponseEntity<>(cityCrudService.insert(dto), HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CityDto> update(@Valid @RequestBody CityDto dto, @PathVariable Long id) {
		log.debug("REST request to update city with id {}: {}", id, dto);

		try {
			dto = cityCrudService.update(dto, id);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete city with id {}", id);

		try {
			cityCrudService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessRuleException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
