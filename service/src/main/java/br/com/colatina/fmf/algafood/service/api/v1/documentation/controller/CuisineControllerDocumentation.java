package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface CuisineControllerDocumentation {
	CollectionModel<CuisineDto> findAll();

	ResponseEntity<CuisinesXmlWrapper> findAllXml();

	ResponseEntity<CuisineDto> findById(Long id);

	ResponseEntity<CuisineDto> findFirst();

	ResponseEntity<CuisineDto> insert(CuisineDto dto);

	ResponseEntity<CuisineDto> update(Long id, CuisineDto dto);

	ResponseEntity<Void> delete(Long id);
}
