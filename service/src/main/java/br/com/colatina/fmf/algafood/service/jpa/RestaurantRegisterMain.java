package br.com.colatina.fmf.algafood.service.jpa;

import br.com.colatina.fmf.algafood.service.FmfAlgafoodServiceApplication;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Slf4j
public class RestaurantRegisterMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FmfAlgafoodServiceApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);

		Restaurant restaurant = new Restaurant();
		restaurant.setName("Restaurante AAA");
		restaurant.setFreightRate(10.5);
		restaurant = restaurantRepository.save(restaurant);
		log.debug(String.format("SAVE = %d - %s - %f", restaurant.getId(), restaurant.getName(), restaurant.getFreightRate()));

		List<Restaurant> restaurants = restaurantRepository.findAll();
		restaurants.forEach(value -> log.debug(String.format("LIST = %d - %s - %f", value.getId(), value.getName(), value.getFreightRate())));

		restaurant = restaurantRepository.findById(restaurant.getId());
		log.debug(String.format("FIND BY ID = %d - %s - %f", restaurant.getId(), restaurant.getName(), restaurant.getFreightRate()));

		restaurantRepository.delete(restaurant);
	}
}
