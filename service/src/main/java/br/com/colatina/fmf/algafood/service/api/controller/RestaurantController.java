package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.RestaurantControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.hateoas.RestaurantHateoas;
import br.com.colatina.fmf.algafood.service.api.hateoas.RestaurantListHateoas;
import br.com.colatina.fmf.algafood.service.core.pageable.PageableTranslator;
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
@RequestMapping(path = "/api/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerDocumentation {
	private final RestaurantCrudService restaurantCrudService;
	private final RestaurantHateoas restaurantHateoas;
	private final RestaurantListHateoas restaurantListHateoas;

	@Override
	@GetMapping()
	public ResponseEntity<CollectionModel<RestaurantListDto>> findAll() {
		log.debug("REST request to find all restaurants");
		List<RestaurantListDto> restaurants = restaurantCrudService.findAll();
		CollectionModel<RestaurantListDto> collectionModel = restaurantListHateoas.mapCollectionModel(restaurants);
		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}

	@Override
	@GetMapping("/freight-fee")
	public ResponseEntity<CollectionModel<RestaurantListDto>> filterByFreightFee(@RequestParam(value = "name", required = false) String name,
																				 @RequestParam(value = "min", required = false) Double min,
																				 @RequestParam(value = "max", required = false) Double max) {
		if (Strings.isEmpty(name)) {
			return _filterByFreightFee(min, max);
		}
		return _filterByFreightFee(name, min, max);
	}

	@Override
	@GetMapping("/page")
	public ResponseEntity<PagedModel<RestaurantListDto>> page(RestaurantPageFilter filter, Pageable pageable) {
		log.debug("REST request to perform a paged search of restaurants with filters {} and with the page configuration {}", filter, pageable);
		pageable = PageableTranslator.translate(pageable, RestaurantListDto.class);
		Page<RestaurantListDto> page = restaurantCrudService.page(filter, pageable);
		PagedModel<RestaurantListDto> pagedModel = restaurantListHateoas.mapPagedModel(page);
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	@Override
	@GetMapping("/first")
	public ResponseEntity<RestaurantDto> findFirst() {
		log.debug("REST request to find the first restaurant it can");
		RestaurantDto restaurant = restaurantCrudService.findFirst();
		restaurantHateoas.mapModel(restaurant);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<RestaurantDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the restaurant with ID: {}", id);
		RestaurantDto restaurant = restaurantCrudService.findDtoById(id);
		restaurantHateoas.mapModel(restaurant);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<RestaurantDto> insert(@Valid @RequestBody RestaurantFormDto dto) {
		log.debug("REST request to insert a new restaurant: {}", dto);
		RestaurantDto restaurant = restaurantCrudService.insert(dto);
		restaurantHateoas.mapModel(restaurant);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<RestaurantDto> update(@Valid @RequestBody RestaurantFormDto dto, @PathVariable Long id) {
		log.debug("REST request to update restaurant with id {}: {}", id, dto);
		RestaurantDto restaurant = restaurantCrudService.update(dto, id);
		restaurantHateoas.mapModel(restaurant);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@Override
	@PatchMapping("/{id}/active")
	public ResponseEntity<Void> toggleActive(@PathVariable Long id, @RequestParam(value = "value") Boolean active) {
		log.debug("REST request to toggle the active status of restaurant {} with the value: {}", id, active);
		restaurantCrudService.toggleActive(id, active);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PatchMapping("/active")
	public ResponseEntity<Void> toggleAllActive(@RequestParam(value = "value") Boolean active, @RequestBody List<Long> restaurantIds) {
		log.debug("REST request to toggle the active status of all restaurants {} with the value: {}", restaurantIds, active);
		restaurantCrudService.toggleActive(restaurantIds, active);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PatchMapping("/{id}/open")
	public ResponseEntity<Void> toggleOpen(@PathVariable Long id, @RequestParam(value = "value") Boolean open) {
		log.debug("REST request to toggle the open status of restaurant {} with the value: {}", id, open);
		restaurantCrudService.toggleOpen(id, open);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete restaurant with id {}", id);
		restaurantCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private ResponseEntity<CollectionModel<RestaurantListDto>> _filterByFreightFee(Double min, Double max) {
		log.debug("REST request to find all restaurants with freight fee between {} and {}", min, max);
		List<RestaurantListDto> restaurants = restaurantCrudService.filterByFreightFee(min, max);
		CollectionModel<RestaurantListDto> collectionModel = restaurantListHateoas.mapCollectionModel(restaurants);
		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}

	private ResponseEntity<CollectionModel<RestaurantListDto>> _filterByFreightFee(String name, Double min, Double max) {
		log.debug("REST request to find all restaurants with name like \"{}\" and freight fee between {} and {}", name, min, max);
		List<RestaurantListDto> restaurants = restaurantCrudService.filterByFreightFee(name, min, max);
		CollectionModel<RestaurantListDto> collectionModel = restaurantListHateoas.mapCollectionModel(restaurants);
		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}
}
