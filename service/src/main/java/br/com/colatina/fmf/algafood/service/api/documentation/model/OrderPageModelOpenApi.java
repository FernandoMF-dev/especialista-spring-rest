package br.com.colatina.fmf.algafood.service.api.documentation.model;

import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PageModel <Order (Listed)>")
public class OrderPageModelOpenApi {
	private EmbeddedOrderPageModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PageModelOpenApi page;

	@Data
	private static class EmbeddedOrderPageModelOpenApi {
		private OrderListDto[] orders;
	}
}
