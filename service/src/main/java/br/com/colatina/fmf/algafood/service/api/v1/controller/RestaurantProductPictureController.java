package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.RestaurantProductPictureControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.ProductHateoas;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.domain.service.ProductPictureCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/restaurants/{restaurantId}/products/{productId}/pictures", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductPictureController implements RestaurantProductPictureControllerDocumentation {
	private final ProductPictureCrudService productPictureCrudService;
	private final ProductHateoas productHateoas;
	private final FileStorageService fileStorageService;

	@Override
	@GetMapping
	@CheckSecurity.Restaurant.Product.Read
	public ResponseEntity<ProductPictureDto> findPicture(@PathVariable Long restaurantId, @PathVariable Long productId) {
		log.debug("REST request to get the data of the picture for the product {} from the restaurant {}", productId, restaurantId);
		ProductPictureDto result = productPictureCrudService.findPictureDto(restaurantId, productId);
		productHateoas.mapPictureModel(result, restaurantId, productId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	@GetMapping(produces = MediaType.ALL_VALUE)
	@CheckSecurity.Restaurant.Product.Read
	public ResponseEntity<InputStreamResource> getPictureFile(@PathVariable Long restaurantId, @PathVariable Long productId,
															  @RequestHeader("accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		log.debug("REST request to the picture file for the product {} from the restaurant {}", productId, restaurantId);
		try {
			ProductPictureDto picture = productPictureCrudService.findPictureDto(restaurantId, productId);
			FileStorageService.RestoredFile restoredFile = fileStorageService.restoreFile(picture.getFileName());
			MediaType contentType = MediaType.parseMediaType(picture.getContentType());

			validateMediaType(acceptHeader, contentType);
			return getPictureFileResponse(restoredFile, contentType);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@CheckSecurity.Restaurant.Product.Update
	public ResponseEntity<ProductPictureDto> updatePicture(@PathVariable Long restaurantId, @PathVariable Long productId,
														   @Valid ProductPictureInsertDto picture) throws IOException {
		log.debug("REST request to upload a picture for the product {} from the restaurant {}", productId, restaurantId);
		var result = productPictureCrudService.save(picture, restaurantId, productId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	@DeleteMapping
	@CheckSecurity.Restaurant.Product.Update
	public ResponseEntity<Void> deletePicture(@PathVariable Long restaurantId, @PathVariable Long productId) {
		log.debug("REST request to delete the picture for the product {} from the restaurant {}", productId, restaurantId);
		productPictureCrudService.delete(restaurantId, productId);
		return ResponseEntity.noContent().build();
	}

	private void validateMediaType(String acceptHeader, MediaType contentType) throws HttpMediaTypeNotAcceptableException {
		List<MediaType> acceptedTypes = MediaType.parseMediaTypes(acceptHeader);
		boolean compatible = acceptedTypes.stream().anyMatch(mediaType -> mediaType.isCompatibleWith(contentType));
		if (!compatible) {
			throw new HttpMediaTypeNotAcceptableException(acceptedTypes);
		}
	}

	private ResponseEntity<InputStreamResource> getPictureFileResponse(FileStorageService.RestoredFile restoredFile, MediaType contentType) {
		if (restoredFile.hasUrl()) {
			return ResponseEntity.status(HttpStatus.FOUND)
					.header(HttpHeaders.LOCATION, restoredFile.getUrl())
					.build();
		}
		return ResponseEntity.ok()
				.contentType(contentType)
				.body(new InputStreamResource(restoredFile.getInputStream()));
	}
}
