package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface AppUserProfileControllerDocumentation {
	CollectionModel<ProfileDto> findAll(Long userId);

	ResponseEntity<Void> associate(Long userId, Long profileId);

	ResponseEntity<Void> disassociate(Long userId, Long profileId);
}
