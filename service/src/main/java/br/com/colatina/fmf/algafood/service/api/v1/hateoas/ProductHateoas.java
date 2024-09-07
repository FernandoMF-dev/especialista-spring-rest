package br.com.colatina.fmf.algafood.service.api.v1.hateoas;

import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantProductController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantProductPictureController;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductHateoas extends EntityHateoas<ProductDto> {
	public ProductHateoas() {
		super(ProductDto.class);
	}

	@Override
	protected void addModelHypermediaLinks(ProductDto model) {
		model.add(linkTo(methodOn(RestaurantProductController.class).findById(model.getRestaurantId(), model.getId())).withSelfRel());
		model.add(linkTo(methodOn(RestaurantProductController.class).findAll(model.getRestaurantId())).withRel(IanaLinkRelations.COLLECTION));
		model.add(linkTo(methodOn(RestaurantProductPictureController.class).findPicture(model.getRestaurantId(), model.getId())).withRel("picture"));
		model.add(linkTo(methodOn(RestaurantController.class).findById(model.getRestaurantId())).withRel("restaurant"));
	}

	@Override
	protected void addCollectionHypermediaLinks(CollectionModel<ProductDto> collection) {
		// Don't have access to the restaurantId here
	}

	public Link createCollectionSelfLink(Long restaurantId) {
		return linkTo(methodOn(RestaurantProductController.class).findAll(restaurantId)).withSelfRel();
	}

	public void mapPictureModel(ProductPictureDto picture, Long restaurantId, Long productId) {
		picture.add(linkTo(methodOn(RestaurantProductPictureController.class).findPicture(restaurantId, productId)).withSelfRel());
		picture.add(linkTo(methodOn(RestaurantProductController.class).findById(restaurantId, productId)).withRel("product"));
	}
}
