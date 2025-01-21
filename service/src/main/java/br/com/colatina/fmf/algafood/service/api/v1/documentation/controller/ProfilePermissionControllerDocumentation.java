package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface ProfilePermissionControllerDocumentation {

	CollectionModel<PermissionDto> findAll(Long profileId);

	ResponseEntity<Void> associate(Long profileId, Long permissionId);

	ResponseEntity<Void> disassociate(Long profileId, Long permissionId);
}
