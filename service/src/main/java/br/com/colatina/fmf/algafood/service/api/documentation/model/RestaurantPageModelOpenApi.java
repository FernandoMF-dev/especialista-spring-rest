package br.com.colatina.fmf.algafood.service.api.documentation.model;

import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PageModel <Restaurant (Listed)>")
public class RestaurantPageModelOpenApi {
	private EmbeddedRestaurantPageModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PageModelOpenApi page;

	@Data
	private static class EmbeddedRestaurantPageModelOpenApi {
		private RestaurantListDto[] restaurants;
	}
}
