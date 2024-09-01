package br.com.colatina.fmf.algafood.service.api.documentation.model.collection;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CollectionModel <User>")
public class UserCollectionModelOpenApi {
	private EmbeddedUserCollectionModelOpenApi _embedded;
	private LinksModelOpenApi _links;

	@Data
	private static class EmbeddedUserCollectionModelOpenApi {
		private UserDto[] users;
	}
}
