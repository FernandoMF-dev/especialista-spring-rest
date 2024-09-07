package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.domain.service.dto.AddressDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
public class AddressHateoas extends EntityHateoas<AddressDto> {
	public AddressHateoas(CityHateoas cityHateoas) {
		super(AddressDto.class, cityHateoas);
	}

	@Override
	protected void addModelHypermediaLinks(AddressDto model) {
		// AddressDto has no hypermedia links
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<AddressDto> collection) {
		// AddressDto has no hypermedia links
	}
}
