package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <Profile>")
public class ProfileCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedProfileCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <Profile>")
	private static class EmbeddedProfileCollectionModelOpenApi {
		@Schema(name = "profiles")
		private ProfileDto[] profiles;
	}
}
