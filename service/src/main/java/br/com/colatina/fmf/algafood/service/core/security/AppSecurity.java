package br.com.colatina.fmf.algafood.service.core.security;

import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppSecurity {
	private final RestaurantRepository restaurantRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Long getUserId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("user_id");
	}

	public boolean managesRestaurant(Long restaurantId) {
		return restaurantRepository.existsResponsable(restaurantId, getUserId());
	}
}
