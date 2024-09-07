package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <Cuisine>")
public class CuisineCollectionModelOpenApi {
	private EmbeddedCuisineCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <Cuisine>")
	private static class EmbeddedCuisineCollectionModelOpenApi {
		private CuisineDto[] cuisines;
	}
}
