package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <State>")
public class StateCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedStateCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <State>")
	private static class EmbeddedStateCollectionModelOpenApi {
		@Schema(name = "states")
		private StateDto[] states;
	}
}
