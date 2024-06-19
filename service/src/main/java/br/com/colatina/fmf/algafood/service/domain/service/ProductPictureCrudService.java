package br.com.colatina.fmf.algafood.service.domain.service;

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

@Service
@Transactional
@RequiredArgsConstructor
public class ProductPictureCrudService {
	private final ProductPictureMapper productPictureMapper;
	private final ProductRepository productRepository;

	private final ProductCrudService productCrudService;
	private final FileStorageService fileStorageService;

	public ProductPictureDto save(ProductPictureInsertDto dto, Long restaurantId, Long productId) throws IOException {
		Product product = productCrudService.findEntityById(restaurantId, productId);
		ProductPicture entity = productPictureMapper.toEntity(dto);
		String fileName = fileStorageService.generateFileName(entity.getFileName());

		entity.setId(productId);
		entity.setProduct(product);
		entity.setFileName(fileName);

		entity = productRepository.save(entity);
		productRepository.flush();
		savePicture(entity, dto.getFile().getInputStream());

		return productPictureMapper.toDto(entity);
	}

	private void savePicture(ProductPicture entity, InputStream inputStream) {
		FileStorageService.NewFile newPicture = FileStorageService.NewFile.builder()
				.fileName(entity.getFileName())
				.inputStream(inputStream)
				.build();

		fileStorageService.store(newPicture);
	}
}
