package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.RestaurantProductControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.hateoas.ProductHateoas;
import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.domain.service.ProductCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
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
@RequestMapping(path = "/api/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController implements RestaurantProductControllerDocumentation {
	private final ProductCrudService productCrudService;
	private final ProductHateoas productHateoas;

	@Override
	@GetMapping()
	public CollectionModel<ProductDto> findAll(@PathVariable Long restaurantId) {
		log.debug("REST request to find all products from restaurant {}", restaurantId);
		List<ProductDto> products = productCrudService.findAll(restaurantId);
		CollectionModel<ProductDto> collection = productHateoas.mapCollectionModel(products);
		collection.removeLinks().add(productHateoas.createCollectionSelfLink(restaurantId));
		return collection;
	}

	@Override
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
		log.debug("REST request to find the product with id {} from restaurant {}", productId, restaurantId);
		ProductDto product = productCrudService.findDtoById(restaurantId, productId);
		return new ResponseEntity<>(productHateoas.mapModel(product), HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<ProductDto> insert(@PathVariable Long restaurantId, @Valid @RequestBody ProductDto dto) {
		log.debug("REST request to insert a new product in restaurant {}: {}", restaurantId, dto);
		ProductDto product = productCrudService.insert(restaurantId, dto);
		ResourceUriUtils.addLocationUriInResponseHeader(product.getId());
		return new ResponseEntity<>(productHateoas.mapModel(product), HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> update(@PathVariable Long restaurantId, @PathVariable Long productId,
											 @Valid @RequestBody ProductDto dto) {
		log.debug("REST request to update product with id {} in restaurant {}: {}", productId, restaurantId, dto);
		ProductDto product = productCrudService.update(restaurantId, dto, productId);
		return new ResponseEntity<>(productHateoas.mapModel(product), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long productId) {
		log.debug("REST request to delete product with id {} from restaurant {}", productId, restaurantId);
		productCrudService.delete(restaurantId, productId);
		return ResponseEntity.noContent().build();
	}
}
