package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.RestaurantControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.RestaurantHateoas;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.RestaurantListHateoas;
import br.com.colatina.fmf.algafood.service.core.pageable.PageableTranslator;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(path = "/api/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerDocumentation {
	private final RestaurantCrudService restaurantCrudService;
	private final RestaurantHateoas restaurantHateoas;
	private final RestaurantListHateoas restaurantListHateoas;

	@Override
	@GetMapping()
	@CheckSecurity.Restaurant.Read
	public ResponseEntity<CollectionModel<RestaurantListDto>> findAll() {
		log.debug("REST request to find all restaurants");
		List<RestaurantListDto> restaurants = restaurantCrudService.findAll();
		return new ResponseEntity<>(restaurantListHateoas.mapCollectionModel(restaurants), HttpStatus.OK);
	}

	@Override
	@GetMapping("/freight-fee")
	@CheckSecurity.Restaurant.Read
	public ResponseEntity<CollectionModel<RestaurantListDto>> filterByFreightFee(@RequestParam(value = "name", required = false) String name,
																				 @RequestParam(value = "min", required = false) Double min,
																				 @RequestParam(value = "max", required = false) Double max) {
		if (Strings.isEmpty(name)) {
			return new ResponseEntity<>(_filterByFreightFee(min, max), HttpStatus.OK);
		}
		return new ResponseEntity<>(_filterByFreightFee(name, min, max), HttpStatus.OK);
	}

	@Override
	@GetMapping("/page")
	@CheckSecurity.Restaurant.Read
	public ResponseEntity<PagedModel<RestaurantListDto>> page(RestaurantPageFilter filter, Pageable pageable) {
		log.debug("REST request to perform a paged search of restaurants with filters {} and with the page configuration {}", filter, pageable);
		pageable = PageableTranslator.translate(pageable, RestaurantListDto.class);
		Page<RestaurantListDto> page = restaurantCrudService.page(filter, pageable);
		return new ResponseEntity<>(restaurantListHateoas.mapPagedModel(page), HttpStatus.OK);
	}

	@Override
	@GetMapping("/first")
	@CheckSecurity.Restaurant.Read
	public ResponseEntity<RestaurantDto> findFirst() {
		log.debug("REST request to find the first restaurant it can");
		RestaurantDto restaurant = restaurantCrudService.findFirst();
		return new ResponseEntity<>(restaurantHateoas.mapModel(restaurant), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	@CheckSecurity.Restaurant.Read
	public ResponseEntity<RestaurantDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the restaurant with ID: {}", id);
		RestaurantDto restaurant = restaurantCrudService.findDtoById(id);
		return new ResponseEntity<>(restaurantHateoas.mapModel(restaurant), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	@CheckSecurity.Restaurant.Create
	public ResponseEntity<RestaurantDto> insert(@Valid @RequestBody RestaurantFormDto dto) {
		log.debug("REST request to insert a new restaurant: {}", dto);
		RestaurantDto restaurant = restaurantCrudService.insert(dto);
		ResourceUriUtils.addLocationUriInResponseHeader(restaurant.getId());
		return new ResponseEntity<>(restaurantHateoas.mapModel(restaurant), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	@CheckSecurity.Restaurant.Update
	public ResponseEntity<RestaurantDto> update(@Valid @RequestBody RestaurantFormDto dto, @PathVariable Long id) {
		log.debug("REST request to update restaurant with id {}: {}", id, dto);
		RestaurantDto restaurant = restaurantCrudService.update(dto, id);
		return new ResponseEntity<>(restaurantHateoas.mapModel(restaurant), HttpStatus.OK);
	}

	@Override
	@PatchMapping("/{id}/active")
	@CheckSecurity.Restaurant.Activate
	public ResponseEntity<Void> toggleActive(@PathVariable Long id, @RequestParam(value = "value") Boolean active) {
		log.debug("REST request to toggle the active status of restaurant {} with the value: {}", id, active);
		restaurantCrudService.toggleActive(id, active);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PatchMapping("/active")
	@CheckSecurity.Restaurant.Activate
	public ResponseEntity<Void> toggleAllActive(@RequestParam(value = "value") Boolean active, @RequestBody List<Long> restaurantIds) {
		log.debug("REST request to toggle the active status of all restaurants {} with the value: {}", restaurantIds, active);
		restaurantCrudService.toggleActive(restaurantIds, active);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PatchMapping("/{id}/open")
	@CheckSecurity.Restaurant.Open
	public ResponseEntity<Void> toggleOpen(@PathVariable Long id, @RequestParam(value = "value") Boolean open) {
		log.debug("REST request to toggle the open status of restaurant {} with the value: {}", id, open);
		restaurantCrudService.toggleOpen(id, open);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{id}")
	@CheckSecurity.Restaurant.Delete
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete restaurant with id {}", id);
		restaurantCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private CollectionModel<RestaurantListDto> _filterByFreightFee(Double min, Double max) {
		log.debug("REST request to find all restaurants with freight fee between {} and {}", min, max);
		List<RestaurantListDto> restaurants = restaurantCrudService.filterByFreightFee(min, max);
		return restaurantListHateoas.mapCollectionModel(restaurants);
	}

	private CollectionModel<RestaurantListDto> _filterByFreightFee(String name, Double min, Double max) {
		log.debug("REST request to find all restaurants with name like \"{}\" and freight fee between {} and {}", name, min, max);
		List<RestaurantListDto> restaurants = restaurantCrudService.filterByFreightFee(name, min, max);
		return restaurantListHateoas.mapCollectionModel(restaurants);
	}
}
