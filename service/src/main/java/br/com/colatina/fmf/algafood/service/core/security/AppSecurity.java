package br.com.colatina.fmf.algafood.service.core.security;

import br.com.colatina.fmf.algafood.service.domain.repository.OrderRepository;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AppSecurity {
	private final RestaurantRepository restaurantRepository;
	private final OrderRepository orderRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Long getUserId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("user_id");
	}

	public boolean isAuthenticatedUser(Long userId) {
		if (Objects.isNull(userId) || Objects.isNull(getUserId())) {
			return false;
		}
		return userId.equals(getUserId());
	}

	public boolean managesRestaurant(Long restaurantId) {
		if (Objects.isNull(restaurantId)) {
			return false;
		}
		return restaurantRepository.existsResponsible(restaurantId, getUserId());
	}

	public boolean managesOrderRestaurant(String orderUuid) {
		if (Objects.isNull(orderUuid)) {
			return false;
		}
		return orderRepository.existsResponsibleByUuid(orderUuid, getUserId());
	}
}
