package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantFormMapper;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantMapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantFactory extends BaseEntityFactory<Restaurant> {
	@Autowired
	RestaurantMapper restaurantMapper;
	@Autowired
	RestaurantFormMapper restaurantFormMapper;
	@Autowired
	RestaurantCrudService restaurantCrudService;
	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	AddressFactory addressFactory;
	@Autowired
	PaymentMethodFactory paymentMethodFactory;
	@Autowired
	CuisineFactory cuisineFactory;

	@Override
	public Restaurant createEntity() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(String.format("Restaurant %d", System.currentTimeMillis()));
		restaurant.setFreightFee(RandomUtils.nextDouble(0.0, 20.0));
		restaurant.setActive(Boolean.TRUE);
		restaurant.setCuisine(cuisineFactory.createAndPersist());
		restaurant.setAddress(addressFactory.createEntity());
		restaurant.setPaymentMethods(List.of(paymentMethodFactory.createAndPersist()));
		return restaurant;
	}

	@Override
	protected Restaurant persist(Restaurant entity) {
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		RestaurantDto saved = restaurantCrudService.insert(dto);
		return restaurantMapper.toEntity(saved);
	}

	@Override
	public Restaurant getById(Long id) {
		return restaurantRepository.findById(id).orElse(null);
	}
}
