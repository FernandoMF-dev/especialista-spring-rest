package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <Product>")
public class ProductCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedProductCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <Product>")
	private static class EmbeddedProductCollectionModelOpenApi {
		@Schema(name = "products")
		private ProductDto[] products;
	}
}
