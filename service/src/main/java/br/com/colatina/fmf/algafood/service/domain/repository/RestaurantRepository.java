package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

	List<Restaurant> findAll();

	Restaurant findById(Long id);

	Restaurant save(Restaurant entity);

	void delete(Restaurant entity);

}
