package br.com.colatina.fmf.algafood.service.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@ApiModel(value = "Model <City>", description = "Representation model for city")
@Relation(collectionRelation = "cities")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CityModelV2 extends RepresentationModel<CityModelV2> implements Serializable {
	@ApiModelProperty(value = "ID of the city", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@ApiModelProperty(value = "Acronym of the city", example = "VIX")
	private String acronym;

	@ApiModelProperty(value = "Full name of the city", example = "Vitória")
	private String name;

	@ApiModelProperty(value = "ID of the state on which the city is located", example = "1")
	private Long stateId;

	@ApiModelProperty(value = "Acronym of the state on which the city is located", example = "ES")
	private String stateAcronym;

	@ApiModelProperty(value = "Full name of the state on which the city is located", example = "Espírito Santo")
	private String stateName;
}
