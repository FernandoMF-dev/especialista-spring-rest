package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.BusinessRule;
import br.com.colatina.fmf.algafood.service.domain.service.ProductCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
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
@RequestMapping("/api/restaurants/{restaurantId}/products")
public class RestaurantProductController {
	private final ProductCrudService productCrudService;

	@GetMapping()
	public ResponseEntity<List<ProductDto>> findAll(@PathVariable Long restaurantId) {
		log.debug("REST request to find all products from restaurant {}", restaurantId);

		try {
			return new ResponseEntity<>(productCrudService.findAll(restaurantId), HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
		log.debug("REST request to find the product with id {} from restaurant {}", productId, restaurantId);

		try {
			ProductDto state = productCrudService.findDtoById(restaurantId, productId);
			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PostMapping()
	public ResponseEntity<ProductDto> insert(@PathVariable Long restaurantId, @Valid @RequestBody ProductDto dto) {
		log.debug("REST request to insert a new product in restaurant {}: {}", restaurantId, dto);

		try {
			return new ResponseEntity<>(productCrudService.insert(restaurantId, dto), HttpStatus.CREATED);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> update(@PathVariable Long restaurantId, @PathVariable Long productId,
											 @Valid @RequestBody ProductDto dto) {
		log.debug("REST request to update product with id {} in restaurant {}: {}", productId, restaurantId, dto);

		try {
			dto = productCrudService.update(restaurantId, dto, productId);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long productId) {
		log.debug("REST request to delete product with id {} from restaurant {}", productId, restaurantId);

		try {
			productCrudService.delete(restaurantId, productId);
			return ResponseEntity.noContent().build();
		} catch (BusinessRule e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getResponseStatus());
		}
	}
}
