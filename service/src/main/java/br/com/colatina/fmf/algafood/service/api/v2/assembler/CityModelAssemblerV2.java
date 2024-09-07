package br.com.colatina.fmf.algafood.service.api.v2.assembler;

import br.com.colatina.fmf.algafood.service.api.v2.controller.CityControllerV2;
import br.com.colatina.fmf.algafood.service.api.v2.model.CityModelV2;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityModelAssemblerV2 extends RepresentationModelAssemblerSupport<CityDto, CityModelV2> {
	private final ModelMapper modelMapper;

	public CityModelAssemblerV2(ModelMapper modelMapper) {
		super(CityControllerV2.class, CityModelV2.class);
		this.modelMapper = modelMapper;
	}

	@Override
	public CityModelV2 toModel(CityDto dto) {
		CityModelV2 model = createModelWithId(dto.getId(), dto);

		modelMapper.map(dto, model);

		model.add(linkTo(methodOn(CityControllerV2.class).findAll()).withRel(IanaLinkRelations.COLLECTION));

		return model;
	}

	@Override
	public CollectionModel<CityModelV2> toCollectionModel(Iterable<? extends CityDto> entities) {
		return super.toCollectionModel(entities).add(linkTo(methodOn(CityControllerV2.class).findAll()).withSelfRel());
	}

}
