package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <Restaurant (Listed)>")
public class RestaurantCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedRestaurantCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <Restaurant (Listed)>")
	private static class EmbeddedRestaurantCollectionModelOpenApi {
		@Schema(name = "restaurants")
		private RestaurantListDto[] restaurants;
	}
}
