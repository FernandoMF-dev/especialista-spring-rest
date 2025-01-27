package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <Order (Listed)>")
public class OrderCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedOrderCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <Order (Listed)>")
	private static class EmbeddedOrderCollectionModelOpenApi {
		@Schema(name = "orders")
		private OrderListDto[] orders;
	}
}
