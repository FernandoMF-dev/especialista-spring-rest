package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.PaymentMethodController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PaymentMethodHateoas extends EntityHateoas<PaymentMethodDto> {
	public PaymentMethodHateoas() {
		super(PaymentMethodDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(PaymentMethodDto model) {
		model.add(linkTo(PaymentMethodController.class).slash(model.getId()).withSelfRel());
		model.add(linkTo(PaymentMethodController.class).withRel(IanaLinkRelations.COLLECTION));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<PaymentMethodDto> collection) {
		collection.add(linkTo(PaymentMethodController.class).withSelfRel());
	}
}
