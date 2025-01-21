package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface StateControllerDocumentation {
	CollectionModel<StateDto> findAll();

	ResponseEntity<StateDto> findById(Long id);

	ResponseEntity<StateDto> insert(StateDto dto);

	ResponseEntity<StateDto> update(Long id, StateDto dto);

	ResponseEntity<Void> delete(Long id);
}
