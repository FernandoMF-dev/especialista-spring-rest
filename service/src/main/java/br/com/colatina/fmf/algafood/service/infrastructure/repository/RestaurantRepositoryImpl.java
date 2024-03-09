package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.RestaurantRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RestaurantDto> filterDtoByFreightRate(Double minFreightRate, Double maxFreightRate) {
		StringBuilder jpql = new StringBuilder("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto" +
				"(r.id, r.name, r.freightRate, r.registrationDate, r.updateDate, r.active, k.id) " +
				" FROM Restaurant r LEFT JOIN r.kitchen k " +
				" WHERE r.excluded = FALSE ");

		HashMap<String, Object> parameters = new HashMap<>();

		if (Objects.nonNull(minFreightRate)) {
			jpql.append(" AND r.freightRate >= :minFreightRate ");
			parameters.put("minFreightRate", minFreightRate);
		}

		if (Objects.nonNull(maxFreightRate)) {
			jpql.append(" AND  r.freightRate <= :maxFreightRate ");
			parameters.put("maxFreightRate", maxFreightRate);
		}

		TypedQuery<RestaurantDto> query = entityManager.createQuery(jpql.toString(), RestaurantDto.class);

		parameters.forEach(query::setParameter);

		return query.getResultList();
	}

	@Override
	public List<Restaurant> filterEntityByFreightRate(String name, Double minFreightRate, Double maxFreightRate) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteria.from(Restaurant.class);
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		predicates.add(builder.isFalse(root.get("excluded")));
		if (Objects.nonNull(minFreightRate)) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("freightRate"), minFreightRate));
		}
		if (Objects.nonNull(maxFreightRate)) {
			predicates.add(builder.lessThanOrEqualTo(root.get("freightRate"), maxFreightRate));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurant> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

}