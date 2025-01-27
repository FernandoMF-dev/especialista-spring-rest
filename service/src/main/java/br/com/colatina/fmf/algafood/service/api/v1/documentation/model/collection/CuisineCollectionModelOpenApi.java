package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <Cuisine>")
public class CuisineCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedCuisineCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <Cuisine>")
	private static class EmbeddedCuisineCollectionModelOpenApi {
		@Schema(name = "cuisines")
		private CuisineDto[] cuisines;
	}
}
