package br.com.colatina.fmf.algafood.service.api.v2.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Schema(name = "Model <City> [V2]", description = "Representation model for city")
@Relation(collectionRelation = "cities")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CityModelV2 extends RepresentationModel<CityModelV2> implements Serializable {
	@Schema(description = "ID of the city", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Acronym of the city", example = "VIX")
	private String acronym;

	@Schema(description = "Full name of the city", example = "Vitória")
	private String name;

	@Schema(description = "ID of the state on which the city is located", example = "1")
	private Long stateId;

	@Schema(description = "Acronym of the state on which the city is located", example = "ES")
	private String stateAcronym;

	@Schema(description = "Full name of the state on which the city is located", example = "Espírito Santo")
	private String stateName;
}
