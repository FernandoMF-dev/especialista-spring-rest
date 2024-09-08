package br.com.colatina.fmf.algafood.service.api.v2.documentation.model;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.v2.model.CityModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <City>")
public class CityCollectionModelOpenApiV2 {
	private CityCollectionModelOpenApiV2.EmbeddedCityCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <City>")
	private static class EmbeddedCityCollectionModelOpenApi {
		private CityModelV2[] cities;
	}
}
