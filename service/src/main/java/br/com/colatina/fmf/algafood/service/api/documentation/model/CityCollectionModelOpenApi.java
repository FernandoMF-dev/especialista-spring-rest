package br.com.colatina.fmf.algafood.service.api.documentation.model;

import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CollectionModel <City>")
public class CityCollectionModelOpenApi {
	private EmbeddedCityCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	private static class EmbeddedCityCollectionModelOpenApi {
		private CityDto[] cities;
	}
}
