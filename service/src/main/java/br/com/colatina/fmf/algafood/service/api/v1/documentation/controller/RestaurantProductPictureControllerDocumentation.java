package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantProductPictureControllerDocumentation {
	ResponseEntity<ProductPictureDto> findPicture(Long restaurantId, Long productId);

	ResponseEntity<InputStreamResource> getPictureFile(Long restaurantId, Long productId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	ResponseEntity<ProductPictureDto> updatePicture(Long restaurantId, Long productId, ProductPictureInsertDto picture) throws IOException;

	ResponseEntity<Void> deletePicture(Long restaurantId, Long productId);
}
