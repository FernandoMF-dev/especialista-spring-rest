package br.com.colatina.fmf.algafood.service.infrastructure.specification;

import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import java.util.Objects;

@Component
public class RestaurantSpecs extends BaseSpecs<Restaurant> {
	@NonNull
	public Specification<Restaurant> byName(String name) {
		return (root, query, criteriaBuilder) -> compareString(criteriaBuilder, root.get("name"), name);
	}

	@NonNull
	public Specification<Restaurant> byMinFreightRate(Double minFreightRate) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(minFreightRate)) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get("freightRate"), minFreightRate);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Restaurant> byMaxFreightRate(Double maxFreightRate) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(maxFreightRate)) {
				return criteriaBuilder.lessThanOrEqualTo(root.get("freightRate"), maxFreightRate);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Restaurant> byActive(Boolean active) {
		return (root, query, criteriaBuilder) -> compareBoolean(criteriaBuilder, root.get("active"), active);
	}

	@NonNull
	public Specification<Restaurant> byExcluded(Boolean excluded) {
		return (root, query, criteriaBuilder) -> compareBoolean(criteriaBuilder, root.get("excluded"), excluded);
	}

	@NonNull
	public Specification<Restaurant> byKitchenId(Long kitchenId) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(kitchenId)) {
				Join<Restaurant, Kitchen> restaurantKitchenJoin = root.join("kitchen");
				return criteriaBuilder.equal(restaurantKitchenJoin.get("id"), kitchenId);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

}
