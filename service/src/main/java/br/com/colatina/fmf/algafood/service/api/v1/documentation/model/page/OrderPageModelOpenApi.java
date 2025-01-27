package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.page;

import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;

@Data
@Schema(name = "Page <Order (Listed)>")
public class OrderPageModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedOrderPageModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Schema(name = "page")
	private PagedModel.PageMetadata page;

	@Data
	@Schema(name = "PageContent <Order (Listed)>")
	private static class EmbeddedOrderPageModelOpenApi {
		@Schema(name = "orders")
		private OrderListDto[] orders;
	}
}
