package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.ProductPictureCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants/{restaurantId}/products/{productId}/pictures")
public class RestaurantProductPictureController {

	private final ProductPictureCrudService productPictureCrudService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProductPictureDto> updatePicture(@PathVariable Long restaurantId, @PathVariable Long productId,
														   @Valid ProductPictureInsertDto picture) {
		log.debug("REST request to upload a picture for the product {} from the restaurant {}", productId, restaurantId);
		var result = productPictureCrudService.save(picture, restaurantId, productId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
