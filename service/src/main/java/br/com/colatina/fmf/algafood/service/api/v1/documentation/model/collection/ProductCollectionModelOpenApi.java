package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <Product>")
public class ProductCollectionModelOpenApi {
	private EmbeddedProductCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <Product>")
	private static class EmbeddedProductCollectionModelOpenApi {
		private ProductDto[] products;
	}
}
