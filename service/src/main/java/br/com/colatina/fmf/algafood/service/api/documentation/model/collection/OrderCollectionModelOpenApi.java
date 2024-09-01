package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <Order (Listed)>")
public class OrderCollectionModelOpenApi {
	private EmbeddedOrderCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <Order (Listed)>")
	private static class EmbeddedOrderCollectionModelOpenApi {
		private OrderListDto[] orders;
	}
}
