package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.repository.ProductRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCrudService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	private final RestaurantCrudService restaurantCrudService;

	public List<ProductDto> findAll(Long restaurantId) {
		validateRestaurant(restaurantId);
		return productRepository.findAllDtoByRestaurant(restaurantId);
	}

	public ProductDto findDtoById(Long restaurantId, Long productId) {
		validateRestaurant(restaurantId);
		return productRepository.findDtoByIdAndRestaurant(restaurantId, productId)
				.orElseThrow(() -> new ResourceNotFoundException("product.not_found.restaurant", productId, restaurantId));
	}

	public Product findEntityById(Long restaurantId, Long productId) {
		validateRestaurant(restaurantId);
		return productRepository.findEntityByIdAndRestaurant(restaurantId, productId)
				.orElseThrow(() -> new ResourceNotFoundException("product.not_found.restaurant", productId, restaurantId));
	}

	public ProductDto insert(Long restaurantId, ProductDto dto) {
		dto.setId(null);
		return save(dto, restaurantId);
	}

	public ProductDto update(Long restaurantId, ProductDto dto, @PathVariable Long productId) {
		ProductDto saved = findDtoById(restaurantId, productId);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved, restaurantId);
	}

	public void delete(Long restaurantId, Long productId) {
		Product saved = findEntityById(restaurantId, productId);
		saved.setExcluded(true);
		productRepository.save(saved);
	}

	private ProductDto save(ProductDto dto, Long restaurantId) {
		dto.setRestaurantId(restaurantId);
		validateRestaurant(dto.getRestaurantId());

		Product entity = productMapper.toEntity(dto);
		entity = productRepository.save(entity);
		return productMapper.toDto(entity);
	}

	private void validateRestaurant(Long restaurantId) {
		restaurantCrudService.findEntityById(restaurantId);
	}
}
