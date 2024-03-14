package br.com.colatina.fmf.algafood.service.domain.repository.queries;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;

import java.util.List;

public interface RestaurantRepositoryQueries {
	List<RestaurantListDto> filterDtoByFreightRate(Double minFreightRate, Double maxFreightRate);

	List<Restaurant> filterEntityByFreightRate(String name, Double minFreightRate, Double maxFreightRate);
}
