package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRule;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	private final RestaurantCrudService restaurantCrudService;

	@GetMapping()
	public ResponseEntity<List<RestaurantDto>> findAll() {
		log.debug("REST request to find all Restaurants");

		try {
			return new ResponseEntity<>(restaurantCrudService.findAll(), HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/freight-rate")
	public ResponseEntity<List<RestaurantDto>> filterByFreightRate(@RequestParam(value = "name", required = false) String name,
																   @RequestParam(value = "min", required = false) Double min,
																   @RequestParam(value = "max", required = false) Double max) {
		if (Strings.isEmpty(name)) {
			return _filterByFreightRate(min, max);
		}
		return _filterByFreightRate(name, min, max);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestaurantDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the Restaurant with ID: {}", id);

		try {
			RestaurantDto restaurant = restaurantCrudService.findDtoById(id);
			return new ResponseEntity<>(restaurant, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PostMapping()
	public ResponseEntity<RestaurantDto> insert(@Valid @RequestBody RestaurantDto dto) {
		log.debug("REST request to insert a new restaurant: {}", dto);

		try {
			return new ResponseEntity<>(restaurantCrudService.insert(dto), HttpStatus.CREATED);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestaurantDto> update(@Valid @RequestBody RestaurantDto dto, @PathVariable Long id) {
		log.debug("REST request to update restaurant with id {}: {}", id, dto);

		try {
			dto = restaurantCrudService.update(dto, id);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete restaurant with id {}", id);

		try {
			restaurantCrudService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	private ResponseEntity<List<RestaurantDto>> _filterByFreightRate(Double min, Double max) {
		log.debug("REST request to find all Restaurants with freight rate between {} and {}", min, max);

		try {
			List<RestaurantDto> result = restaurantCrudService.filterByFreightRate(min, max);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	private ResponseEntity<List<RestaurantDto>> _filterByFreightRate(String name, Double min, Double max) {
		log.debug("REST request to find all Restaurants with name like \"{}\" and freight rate between {} and {}", name, min, max);

		try {
			List<RestaurantDto> result = restaurantCrudService.filterByFreightRate(name, min, max);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
