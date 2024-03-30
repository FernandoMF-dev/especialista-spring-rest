package br.com.colatina.fmf.algafood.service.infrastructure.specification;

import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import java.util.Objects;

@Component
public class RestaurantSpecs extends BaseSpecs<Restaurant> {
	@NonNull
	public Specification<Restaurant> byName(String name) {
		return (root, query, criteriaBuilder) -> compareString(criteriaBuilder, root.get(Restaurant_.name), name);
	}

	@NonNull
	public Specification<Restaurant> byMinFreightFee(Double minFreightFee) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(minFreightFee)) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get(Restaurant_.freightFee), minFreightFee);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Restaurant> byMaxFreightFee(Double maxFreightFee) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(maxFreightFee)) {
				return criteriaBuilder.lessThanOrEqualTo(root.get(Restaurant_.freightFee), maxFreightFee);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Restaurant> byActive(Boolean active) {
		return (root, query, criteriaBuilder) -> compareBoolean(criteriaBuilder, root.get(Restaurant_.active), active);
	}

	@NonNull
	public Specification<Restaurant> byExcluded(Boolean excluded) {
		return (root, query, criteriaBuilder) -> compareBoolean(criteriaBuilder, root.get(Restaurant_.excluded), excluded);
	}

	@NonNull
	public Specification<Restaurant> byKitchenId(Long kitchenId) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(kitchenId)) {
				Join<Restaurant, Kitchen> restaurantKitchenJoin = root.join(Restaurant_.kitchen);
				return criteriaBuilder.equal(restaurantKitchenJoin.get("id"), kitchenId);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

}
