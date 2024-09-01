package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Collection <Permission>")
public class PermissionCollectionModelOpenApi {
	private EmbeddedPermissionCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	@ApiModel("CollectionContent <Permission>")
	private static class EmbeddedPermissionCollectionModelOpenApi {
		private PermissionDto[] permissions;
	}
}
