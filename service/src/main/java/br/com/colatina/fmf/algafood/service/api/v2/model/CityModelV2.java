package br.com.colatina.fmf.algafood.service.api.v2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Relation(collectionRelation = "cities")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CityModelV2 extends RepresentationModel<CityModelV2> implements Serializable {
	@EqualsAndHashCode.Include
	private Long id;

	private String acronym;

	private String name;

	private Long stateId;

	private String stateAcronym;

	private String stateName;
}
