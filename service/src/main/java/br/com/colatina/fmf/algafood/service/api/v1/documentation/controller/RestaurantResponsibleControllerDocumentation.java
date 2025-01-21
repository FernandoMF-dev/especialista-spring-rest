package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface RestaurantResponsibleControllerDocumentation {
	CollectionModel<AppUserDto> findAll(Long restaurantId);

	ResponseEntity<Void> associate(Long restaurantId, Long responsibleId);

	ResponseEntity<Void> disassociate(Long restaurantId, Long responsibleId);
}
