package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.repository.ProductRepository;
import br.com.colatina.fmf.algafood.service.domain.service.ProductCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory extends BaseEntityFactory<Product> {
	@Autowired
	ProductMapper productMapper;
	@Autowired
	ProductCrudService productCrudService;
	@Autowired
	ProductRepository productRepository;

	@Autowired
	RestaurantFactory restaurantFactory;

	@Override
	public Product createEntity() {
		Product product = new Product();
		product.setName(String.format("Product %d", System.currentTimeMillis()));
		product.setDescription(String.format("Description %d", System.currentTimeMillis()));
		product.setPrice(25.5);
		product.setActive(Boolean.TRUE);
		product.setRestaurant(restaurantFactory.createAndPersist());
		return product;
	}

	@Override
	protected Product persist(Product entity) {
		ProductDto dto = productMapper.toDto(entity);
		dto = productCrudService.insert(entity.getRestaurant().getId(), dto);
		return productMapper.toEntity(dto);
	}

	@Override
	public Product getById(Long id) {
		return productRepository.findById(id).orElse(null);
	}
}
