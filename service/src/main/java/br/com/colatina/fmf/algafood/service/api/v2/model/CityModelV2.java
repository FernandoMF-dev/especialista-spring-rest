package br.com.colatina.fmf.algafood.service.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityModelV2 extends RepresentationModel<CityModelV2> implements Serializable {
	@ApiModelProperty(value = "ID of the city", example = "1")
	private Long id;

	@ApiModelProperty(value = "Acronym of the city", example = "VIX", required = true)
	private String acronym;

	@ApiModelProperty(value = "Full name of the city", example = "Vitória", required = true)
	private String name;

	private Long stateId;
	private String stateAcronym;
	private String stateName;
}
