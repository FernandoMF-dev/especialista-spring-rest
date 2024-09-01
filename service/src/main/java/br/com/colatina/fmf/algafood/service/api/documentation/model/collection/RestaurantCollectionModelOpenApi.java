package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CollectionModel <Restaurant (Listed)>")
public class RestaurantCollectionModelOpenApi {
	private EmbeddedRestaurantCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	private static class EmbeddedRestaurantCollectionModelOpenApi {
		private RestaurantListDto[] restaurants;
	}
}
