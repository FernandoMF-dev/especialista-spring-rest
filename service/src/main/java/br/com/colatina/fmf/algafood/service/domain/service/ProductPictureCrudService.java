package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.model.ProductPicture;
import br.com.colatina.fmf.algafood.service.domain.repository.ProductRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProductPictureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductPictureCrudService {
	private final ProductPictureMapper productPictureMapper;
	private final ProductRepository productRepository;

	private final ProductCrudService productCrudService;
	private final FileStorageService fileStorageService;

	public ProductPicture findPictureEntity(Long restaurantId, Long productId) {
		return productRepository.findPictureEntityById(restaurantId, productId)
				.orElseThrow(() -> new ResourceNotFoundException("product.picture.not_found", productId));
	}

	public ProductPictureDto findPictureDto(Long restaurantId, Long productId) {
		return productRepository.findPictureDtoById(restaurantId, productId)
				.orElseThrow(() -> new ResourceNotFoundException("product.picture.not_found", productId));
	}

	public ProductPictureDto save(ProductPictureInsertDto dto, Long restaurantId, Long productId) throws IOException {
		Product product = productCrudService.findEntityById(restaurantId, productId);
		ProductPicture entity = productPictureMapper.toEntity(dto);

		deleteExistingPicture(restaurantId, productId);
		mapPicturePropertiesSave(entity, product);
		entity = productRepository.save(entity);
		productRepository.flush();
		savePicture(entity, dto.getFile().getInputStream());

		return productPictureMapper.toDto(entity);
	}

	public void delete(Long restaurantId, Long productId) {
		ProductPicture picture = findPictureEntity(restaurantId, productId);
		productRepository.delete(picture);
		fileStorageService.remove(picture.getFileName());
	}

	private void mapPicturePropertiesSave(ProductPicture entity, Product product) {
		String fileName = fileStorageService.generateFileName(entity.getFileName());

		entity.setId(product.getId());
		entity.setProduct(product);
		entity.setFileName(fileName);
	}

	private void deleteExistingPicture(Long restaurantId, Long productId) {
		Optional<ProductPicture> saved = productRepository.findPictureEntityById(restaurantId, productId);

		saved.ifPresent(productPicture -> fileStorageService.remove(productPicture.getFileName()));
	}

	private void savePicture(ProductPicture entity, InputStream inputStream) {
		FileStorageService.NewFile newPicture = FileStorageService.NewFile.builder()
				.fileName(entity.getFileName())
				.contentType(entity.getContentType())
				.inputStream(inputStream)
				.build();

		fileStorageService.store(newPicture);
	}
}
