package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.CITIES)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface CityControllerDocumentation {
    CollectionModel<CityDto> findAll();

    ResponseEntity<CityDto> findById(Long id);

    ResponseEntity<CityDto> insert(CityDto dto);

    ResponseEntity<CityDto> update(Long id, CityDto dto);

    ResponseEntity<Void> delete(Long id);
}
