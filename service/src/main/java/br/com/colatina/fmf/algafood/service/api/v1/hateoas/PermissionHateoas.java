package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
public class PermissionHateoas extends EntityHateoas<PermissionDto> {
	public PermissionHateoas() {
		super(PermissionDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(PermissionDto model) {
		// PermissionDto has no hypermedia links
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<PermissionDto> collection) {
		// PermissionDto has no hypermedia links
	}

}
