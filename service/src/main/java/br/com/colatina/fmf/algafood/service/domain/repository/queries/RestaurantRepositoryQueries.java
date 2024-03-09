package br.com.colatina.fmf.algafood.service.domain.repository.queries;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;

import java.util.List;

public interface RestaurantRepositoryQueries {
	List<RestaurantDto> filterDtoByFreightRate(Double minFreightRate, Double maxFreightRate);

	List<Restaurant> filterEntityByFreightRate(String name, Double minFreightRate, Double maxFreightRate);
}
