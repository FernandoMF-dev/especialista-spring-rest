package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface StateControllerDocumentation {
	CollectionModel<StateDto> findAll();

	ResponseEntity<StateDto> findById(Long id);

	ResponseEntity<StateDto> insert(StateDto dto);

	ResponseEntity<StateDto> update(Long id, StateDto dto);

	ResponseEntity<Void> delete(Long id);
}
