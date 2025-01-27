package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <SalesPerPeriod>")
public class SalesPerPeriodCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedSalesPerPeriodCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <SalesPerPeriod>")
	private static class EmbeddedSalesPerPeriodCollectionModelOpenApi {
		@Schema(name = "sales_per_period")
		private SalesPerPeriod[] sales_per_period;
	}
}
