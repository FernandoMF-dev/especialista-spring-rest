package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.page;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.PageModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Page <Order (Listed)>")
public class OrderPageModelOpenApi {
	private EmbeddedOrderPageModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PageModelOpenApi page;

	@Data
	@ApiModel("PageContent <Order (Listed)>")
	private static class EmbeddedOrderPageModelOpenApi {
		private OrderListDto[] orders;
	}
}
