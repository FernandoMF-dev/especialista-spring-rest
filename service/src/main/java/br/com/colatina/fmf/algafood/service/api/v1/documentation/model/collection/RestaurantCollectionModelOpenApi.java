package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <Restaurant (Listed)>")
public class RestaurantCollectionModelOpenApi {
	private EmbeddedRestaurantCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <Restaurant (Listed)>")
	private static class EmbeddedRestaurantCollectionModelOpenApi {
		private RestaurantListDto[] restaurants;
	}
}
