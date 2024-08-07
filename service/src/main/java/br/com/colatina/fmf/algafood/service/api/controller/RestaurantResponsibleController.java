package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/restaurants/{restaurantId}/responsible")
public class RestaurantResponsibleController {
	private final RestaurantCrudService restaurantCrudService;

	@GetMapping()
	public ResponseEntity<Set<UserDto>> findAll(@PathVariable Long restaurantId) {
		log.debug("REST request to find all user responsible for the restaurant {}", restaurantId);
		Set<UserDto> responsibles = restaurantCrudService.findAllResponsiblesByRestaurant(restaurantId);
		return new ResponseEntity<>(responsibles, HttpStatus.OK);
	}

	@PutMapping("/{responsibleId}")
	public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long responsibleId) {
		log.debug("REST request to associate the user {} as responsible for the restaurant {}", responsibleId, restaurantId);
		restaurantCrudService.addResponsibleToRestaurant(restaurantId, responsibleId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{responsibleId}")
	public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long responsibleId) {
		log.debug("REST request to disassociate the user {} from being responsible for the restaurant {}", responsibleId, restaurantId);
		restaurantCrudService.removeResponsibleFromRestaurant(restaurantId, responsibleId);
		return ResponseEntity.noContent().build();
	}
}
