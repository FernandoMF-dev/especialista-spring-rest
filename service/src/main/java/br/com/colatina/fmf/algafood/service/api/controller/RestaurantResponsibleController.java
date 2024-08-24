package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.documentation.controller.RestaurantResponsibleControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.hateoas.UserHateoas;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResponsibleController implements RestaurantResponsibleControllerDocumentation {
	private final RestaurantCrudService restaurantCrudService;
	private final UserHateoas userHateoas;

	@Override
	@GetMapping()
	public ResponseEntity<CollectionModel<UserDto>> findAll(@PathVariable Long restaurantId) {
		log.debug("REST request to find all user responsible for the restaurant {}", restaurantId);
		Set<UserDto> responsibles = restaurantCrudService.findAllResponsiblesByRestaurant(restaurantId);
		CollectionModel<UserDto> collectionModel = userHateoas.mapCollectionModel(responsibles);
		collectionModel.removeLinks().add(userHateoas.createResponsiblesSelfLink(restaurantId));
		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}

	@Override
	@PutMapping("/{responsibleId}")
	public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long responsibleId) {
		log.debug("REST request to associate the user {} as responsible for the restaurant {}", responsibleId, restaurantId);
		restaurantCrudService.addResponsibleToRestaurant(restaurantId, responsibleId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{responsibleId}")
	public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long responsibleId) {
		log.debug("REST request to disassociate the user {} from being responsible for the restaurant {}", responsibleId, restaurantId);
		restaurantCrudService.removeResponsibleFromRestaurant(restaurantId, responsibleId);
		return ResponseEntity.noContent().build();
	}
}
