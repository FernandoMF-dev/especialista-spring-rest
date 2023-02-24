package br.com.colatina.fmf.algafood.service.domain.repository.queries;

import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;

import java.util.List;

public interface RestaurantRepositoryQueries {
	List<RestaurantDto> filterByFreightRate(Double minFreightRate, Double maxFreightRate);
}
