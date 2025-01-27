package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <City>")
public class CityCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedCityCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <City>")
	private static class EmbeddedCityCollectionModelOpenApi {
		@Schema(name = "cities")
		private CityDto[] cities;
	}
}
