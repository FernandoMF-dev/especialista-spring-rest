package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface ProfilePermissionControllerDocumentation {

	CollectionModel<PermissionDto> findAll(Long profileId);

	ResponseEntity<Void> associate(Long profileId, Long permissionId);

	ResponseEntity<Void> disassociate(Long profileId, Long permissionId);
}
