package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface ProfileControllerDocumentation {
	CollectionModel<ProfileDto> findAll();

	ResponseEntity<ProfileDto> findById(Long id);

	ResponseEntity<ProfileDto> insert(ProfileDto dto);

	ResponseEntity<ProfileDto> update(Long id, ProfileDto dto);

	ResponseEntity<Void> delete(Long id);
}
