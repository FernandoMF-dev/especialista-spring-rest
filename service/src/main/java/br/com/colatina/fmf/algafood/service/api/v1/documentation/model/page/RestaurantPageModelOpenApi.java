package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.page;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.PageModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Page <Restaurant (Listed)>")
public class RestaurantPageModelOpenApi {
	private EmbeddedRestaurantPageModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PageModelOpenApi page;

	@Data
	@ApiModel("PageContent <Restaurant (Listed)>")
	private static class EmbeddedRestaurantPageModelOpenApi {
		private RestaurantListDto[] restaurants;
	}
}
