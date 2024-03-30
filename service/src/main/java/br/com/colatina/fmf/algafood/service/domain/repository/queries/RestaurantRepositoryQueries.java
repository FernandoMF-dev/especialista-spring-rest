package br.com.colatina.fmf.algafood.service.domain.repository.queries;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;

import java.util.List;

public interface RestaurantRepositoryQueries {
	List<RestaurantListDto> filterDtoByFreightFee(Double minFreightFee, Double maxFreightFee);

	List<Restaurant> filterEntityByFreightFee(String name, Double minFreightFee, Double maxFreightFee);
}
