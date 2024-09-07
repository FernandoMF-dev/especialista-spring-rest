package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <PaymentMethod>")
public class PaymentMethodCollectionModelOpenApi {
	private EmbeddedPaymentMethodCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <PaymentMethod>")
	private static class EmbeddedPaymentMethodCollectionModelOpenApi {
		private PaymentMethodDto[] payment_methods;
	}
}
