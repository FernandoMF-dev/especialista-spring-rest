package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CollectionModel <Product>")
public class ProductCollectionModelOpenApi {
	private EmbeddedProductCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	private static class EmbeddedProductCollectionModelOpenApi {
		private ProductDto[] products;
	}
}
