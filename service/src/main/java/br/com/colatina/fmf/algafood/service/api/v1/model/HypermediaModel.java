package br.com.colatina.fmf.algafood.service.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

@Schema(name = "HypermediaModel", description = "Representation model for empty response body with hypermedia links")
public class HypermediaModel extends RepresentationModel<HypermediaModel> {
	// This class is used to add hypermedia links to the response body of a REST request
	// when the response body would be normally empty.
}
