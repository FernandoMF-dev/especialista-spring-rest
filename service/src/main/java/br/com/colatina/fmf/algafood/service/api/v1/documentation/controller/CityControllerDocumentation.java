package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.CITIES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface CityControllerDocumentation {
	@Operation(summary = "Find a list of all available cities")
    CollectionModel<CityDto> findAll();

	@Operation(summary = "Find a city by its ID")
    ResponseEntity<CityDto> findById(Long id);

	@Operation(summary = "Insert a new city")
    ResponseEntity<CityDto> insert(CityDto dto);

	@Operation(summary = "Update a city by its ID")
    ResponseEntity<CityDto> update(Long id, CityDto dto);

	@Operation(summary = "Delete a city by its ID")
    ResponseEntity<Void> delete(Long id);
}
