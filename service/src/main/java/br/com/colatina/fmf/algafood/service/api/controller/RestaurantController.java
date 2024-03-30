package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRule;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public ResponseEntity<List<RestaurantListDto>> findAll() {
		log.debug("REST request to find all Restaurants");

		try {
			return new ResponseEntity<>(restaurantCrudService.findAll(), HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/freight-fee")
	public ResponseEntity<List<RestaurantListDto>> filterByFreightFee(@RequestParam(value = "name", required = false) String name,
																	  @RequestParam(value = "min", required = false) Double min,
																	  @RequestParam(value = "max", required = false) Double max) {
		if (Strings.isEmpty(name)) {
			return _filterByFreightFee(min, max);
		}
		return _filterByFreightFee(name, min, max);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<RestaurantListDto>> page(RestaurantPageFilter filter, Pageable pageable) {
		try {
			return new ResponseEntity<>(restaurantCrudService.page(filter, pageable), HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/first")
	public ResponseEntity<RestaurantDto> findFirst() {
		try {
			return new ResponseEntity<>(restaurantCrudService.findFirst(), HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
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

	private ResponseEntity<List<RestaurantListDto>> _filterByFreightFee(Double min, Double max) {
		log.debug("REST request to find all Restaurants with freight fee between {} and {}", min, max);

		try {
			List<RestaurantListDto> result = restaurantCrudService.filterByFreightFee(min, max);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	private ResponseEntity<List<RestaurantListDto>> _filterByFreightFee(String name, Double min, Double max) {
		log.debug("REST request to find all Restaurants with name like \"{}\" and freight fee between {} and {}", name, min, max);

		try {
			List<RestaurantListDto> result = restaurantCrudService.filterByFreightFee(name, min, max);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
