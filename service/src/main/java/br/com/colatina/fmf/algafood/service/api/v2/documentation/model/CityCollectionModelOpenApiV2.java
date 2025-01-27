package br.com.colatina.fmf.algafood.service.api.v2.documentation.model;

import br.com.colatina.fmf.algafood.service.api.v2.model.CityModelV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <City> [V2]")
public class CityCollectionModelOpenApiV2 {
	@Schema(name = "_embedded")
	private EmbeddedCityCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <City> [V2]")
	private static class EmbeddedCityCollectionModelOpenApi {
		@Schema(name = "cities")
		private CityModelV2[] cities;
	}
}
