package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CollectionModel <SalesPerPeriod>")
public class SalesPerPeriodCollectionModelOpenApi {
	private EmbeddedSalesPerPeriodCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	private static class EmbeddedSalesPerPeriodCollectionModelOpenApi {
		private SalesPerPeriod[] sales_per_period;
	}
}
