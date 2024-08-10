package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.RestaurantControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.model.view.RestaurantView;
import br.com.colatina.fmf.algafood.service.core.pageable.PageableTranslator;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/restaurants")
public class RestaurantController implements RestaurantControllerDocumentation {
	private final RestaurantCrudService restaurantCrudService;

	@GetMapping()
	@Override
	public ResponseEntity<List<RestaurantListDto>> findAll() {
		log.debug("REST request to find all restaurants");
		return new ResponseEntity<>(restaurantCrudService.findAll(), HttpStatus.OK);
	}

	@JsonView(RestaurantView.Summary.class)
	@GetMapping(params = {"projection=summary"})
	@Override
	public ResponseEntity<List<RestaurantListDto>> findAllSummary() {
		return this.findAll();
	}

	@JsonView(RestaurantView.NameOnly.class)
	@GetMapping(params = {"projection=name-only"})
	@Override
	public ResponseEntity<List<RestaurantListDto>> findAllNameOnly() {
		return this.findAll();
	}

	@GetMapping("/freight-fee")
	@Override
	public ResponseEntity<List<RestaurantListDto>> filterByFreightFee(@RequestParam(value = "name", required = false) String name,
																	  @RequestParam(value = "min", required = false) Double min,
																	  @RequestParam(value = "max", required = false) Double max) {
		if (Strings.isEmpty(name)) {
			return _filterByFreightFee(min, max);
		}
		return _filterByFreightFee(name, min, max);
	}

	@GetMapping("/page")
	@Override
	public ResponseEntity<Page<RestaurantListDto>> page(RestaurantPageFilter filter, Pageable pageable) {
		log.debug("REST request to perform a paged search of restaurants with filters {} and with the page configuration {}", filter, pageable);
		pageable = PageableTranslator.translate(pageable, RestaurantListDto.class);
		return new ResponseEntity<>(restaurantCrudService.page(filter, pageable), HttpStatus.OK);
	}

	@GetMapping("/first")
	@Override
	public ResponseEntity<RestaurantDto> findFirst() {
		log.debug("REST request to find the first restaurant it can");
		return new ResponseEntity<>(restaurantCrudService.findFirst(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<RestaurantDto> findById(@PathVariable Long id) {
		log.debug("REST request to find the restaurant with ID: {}", id);
		return new ResponseEntity<>(restaurantCrudService.findDtoById(id), HttpStatus.OK);
	}

	@PostMapping()
	@Override
	public ResponseEntity<RestaurantDto> insert(@Valid @RequestBody RestaurantFormDto dto) {
		log.debug("REST request to insert a new restaurant: {}", dto);
		return new ResponseEntity<>(restaurantCrudService.insert(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@Override
	public ResponseEntity<RestaurantDto> update(@Valid @RequestBody RestaurantFormDto dto, @PathVariable Long id) {
		log.debug("REST request to update restaurant with id {}: {}", id, dto);
		return new ResponseEntity<>(restaurantCrudService.update(dto, id), HttpStatus.OK);
	}

	@PatchMapping("/{id}/active")
	@Override
	public ResponseEntity<Void> toggleActive(@PathVariable Long id, @RequestParam(value = "value") Boolean active) {
		log.debug("REST request to toggle the active status of restaurant {} with the value: {}", id, active);
		restaurantCrudService.toggleActive(id, active);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/active")
	@Override
	public ResponseEntity<Void> toggleAllActive(@RequestParam(value = "value") Boolean active, @RequestBody List<Long> restaurantIds) {
		log.debug("REST request to toggle the active status of all restaurants {} with the value: {}", restaurantIds, active);
		restaurantCrudService.toggleActive(restaurantIds, active);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/open")
	@Override
	public ResponseEntity<Void> toggleOpen(@PathVariable Long id, @RequestParam(value = "value") Boolean open) {
		log.debug("REST request to toggle the open status of restaurant {} with the value: {}", id, open);
		restaurantCrudService.toggleOpen(id, open);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete restaurant with id {}", id);
		restaurantCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private ResponseEntity<List<RestaurantListDto>> _filterByFreightFee(Double min, Double max) {
		log.debug("REST request to find all restaurants with freight fee between {} and {}", min, max);
		return new ResponseEntity<>(restaurantCrudService.filterByFreightFee(min, max), HttpStatus.OK);
	}

	private ResponseEntity<List<RestaurantListDto>> _filterByFreightFee(String name, Double min, Double max) {
		log.debug("REST request to find all restaurants with name like \"{}\" and freight fee between {} and {}", name, min, max);
		return new ResponseEntity<>(restaurantCrudService.filterByFreightFee(name, min, max), HttpStatus.OK);
	}
}
