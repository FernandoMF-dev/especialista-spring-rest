package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface ProfileControllerDocumentation {
	CollectionModel<ProfileDto> findAll();

	ResponseEntity<ProfileDto> findById(Long id);

	ResponseEntity<ProfileDto> insert(ProfileDto dto);

	ResponseEntity<ProfileDto> update(Long id, ProfileDto dto);

	ResponseEntity<Void> delete(Long id);
}
