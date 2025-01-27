package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <User>")
public class AppUserCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedUserCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <User>")
	private static class EmbeddedUserCollectionModelOpenApi {
		@Schema(name = "users")
		private AppUserDto[] users;
	}
}
