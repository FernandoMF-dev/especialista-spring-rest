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

@Service
@Transactional
@RequiredArgsConstructor
public class ProductPictureCrudService {
	private final ProductPictureMapper productPictureMapper;
	private final ProductRepository productRepository;

	private final ProductCrudService productCrudService;

	public ProductPictureDto save(ProductPictureInsertDto dto, Long restaurantId, Long productId) {
		Product product = productCrudService.findEntityById(restaurantId, productId);
		ProductPicture entity = productPictureMapper.toEntity(dto);

		entity.setId(productId);
		entity.setProduct(product);

		entity = productRepository.save(entity);
		return productPictureMapper.toDto(entity);
	}
}
