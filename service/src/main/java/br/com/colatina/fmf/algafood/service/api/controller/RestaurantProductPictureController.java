package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
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
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants/{restaurantId}/products/{productId}/pictures")
public class RestaurantProductPictureController {
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> updatePicture(@PathVariable Long restaurantId, @PathVariable Long productId,
											  @Valid ProductPictureDto picture) {
		log.debug("REST request to upload a picture for the product {} from the restaurant {}", productId, restaurantId);

		var file = picture.getFile();
		var fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		var filePath = Path.of("/home/fmartinsf/Imagens/Catalogo/", fileName);

		System.out.println("\n");
		System.out.println(picture.getDescription());
		System.out.println(filePath);
		System.out.println(file.getContentType());
		System.out.println("\n");

		try {
			file.transferTo(filePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
