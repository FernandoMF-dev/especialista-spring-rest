package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RestaurantResponsibleControllerDocumentation {
	CollectionModel<AppUserDto> findAll(Long restaurantId);

	ResponseEntity<Void> associate(Long restaurantId, Long responsibleId);

	ResponseEntity<Void> disassociate(Long restaurantId, Long responsibleId);
}
