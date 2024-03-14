package br.com.colatina.fmf.algafood.service.domain.repository.queries;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepositoryQueries {
	List<Restaurant> filterEntityByFreightRate(String name, Double minFreightRate, Double maxFreightRate);
}
