package br.com.colatina.fmf.algafood.service.infrastructure.specification;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.Order_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import java.time.OffsetDateTime;
import java.util.Objects;

@Component
public class OrderSpecs extends BaseSpecs<Order> {

	public OrderSpecs() {
		super(Order.class);
	}

	@NonNull
	public Specification<Order> byStatus(OrderStatusEnum status) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(status)) {
				return criteriaBuilder.equal(root.get(Order_.status), status);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Order> byRestaurantId(Long restaurantId) {
		return (root, query, criteriaBuilder) -> {
			fetch(root, query, Order_.restaurant);

			if (Objects.nonNull(restaurantId)) {
				Join<Order, Restaurant> orderRestaurantJoin = root.join(Order_.restaurant);
				return criteriaBuilder.equal(orderRestaurantJoin.get("id"), restaurantId);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Order> byClientId(Long client) {
		return (root, query, criteriaBuilder) -> {
			fetch(root, query, Order_.user);

			if (Objects.nonNull(client)) {
				Join<Order, User> orderUserJoin = root.join(Order_.user);
				return criteriaBuilder.equal(orderUserJoin.get("id"), client);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Order> byMinRegistrationDate(OffsetDateTime minRegistrationDate) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(minRegistrationDate)) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.registrationDate), minRegistrationDate);
			}

			return defaultReturn(criteriaBuilder);
		};
	}

	@NonNull
	public Specification<Order> byMaxRegistrationDate(OffsetDateTime maxRegistrationDate) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.nonNull(maxRegistrationDate)) {
				return criteriaBuilder.lessThanOrEqualTo(root.get(Order_.registrationDate), maxRegistrationDate);
			}

			return defaultReturn(criteriaBuilder);
		};
	}
}
