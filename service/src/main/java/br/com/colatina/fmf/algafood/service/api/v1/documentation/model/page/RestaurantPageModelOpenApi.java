package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.page;

import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;

@Data
@Schema(name = "Page <Restaurant (Listed)>")
public class RestaurantPageModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedRestaurantPageModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Schema(name = "page")
	private PagedModel.PageMetadata page;

	@Data
	@Schema(name = "PageContent <Restaurant (Listed)>")
	private static class EmbeddedRestaurantPageModelOpenApi {
		@Schema(name = "restaurants")
		private RestaurantListDto[] restaurants;
	}
}
