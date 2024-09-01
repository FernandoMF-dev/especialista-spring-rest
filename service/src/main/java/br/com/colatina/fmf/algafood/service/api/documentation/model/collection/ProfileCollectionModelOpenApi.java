package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <Profile>")
public class ProfileCollectionModelOpenApi {
	private EmbeddedProfileCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <Profile>")
	private static class EmbeddedProfileCollectionModelOpenApi {
		private ProfileDto[] profiles;
	}
}
