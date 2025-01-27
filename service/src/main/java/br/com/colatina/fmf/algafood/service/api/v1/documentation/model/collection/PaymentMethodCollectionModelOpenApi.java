package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <PaymentMethod>")
public class PaymentMethodCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedPaymentMethodCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <PaymentMethod>")
	private static class EmbeddedPaymentMethodCollectionModelOpenApi {
		@Schema(name = "payment_methods")
		private PaymentMethodDto[] payment_methods;
	}
}
