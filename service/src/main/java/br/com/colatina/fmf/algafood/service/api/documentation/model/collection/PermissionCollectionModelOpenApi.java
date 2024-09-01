package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CollectionModel <Permission>")
public class PermissionCollectionModelOpenApi {
	private EmbeddedPermissionCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	private static class EmbeddedPermissionCollectionModelOpenApi {
		private PermissionDto[] permissions;
	}
}
