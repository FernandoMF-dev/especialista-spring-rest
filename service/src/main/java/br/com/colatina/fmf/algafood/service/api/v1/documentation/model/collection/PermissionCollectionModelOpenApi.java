package br.com.colatina.fmf.algafood.service.api.v1.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

@Data
@Schema(name = "Collection <Permission>")
public class PermissionCollectionModelOpenApi {
	@Schema(name = "_embedded")
	private EmbeddedPermissionCollectionModelOpenApi _embedded;

	@Schema(name = "_links")
	private Links _links;

	@Data
	@Schema(name = "CollectionContent <Permission>")
	private static class EmbeddedPermissionCollectionModelOpenApi {
		@Schema(name = "permissions")
		private PermissionDto[] permissions;
	}
}
