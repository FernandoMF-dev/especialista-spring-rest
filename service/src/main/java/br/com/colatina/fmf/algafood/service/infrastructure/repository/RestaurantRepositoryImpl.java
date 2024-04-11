package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.RestaurantRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
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
	public List<RestaurantListDto> filterDtoByFreightFee(Double minFreightFee, Double maxFreightFee) {
		StringBuilder jpql = new StringBuilder("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto" +
				"(r.id, r.name, r.freightFee, r.active, k.id, k.name) " +
				" FROM Restaurant r LEFT JOIN r.cuisine k " +
				" WHERE r.excluded = FALSE ");

		HashMap<String, Object> parameters = new HashMap<>();

		if (Objects.nonNull(minFreightFee)) {
			jpql.append(" AND r.freightFee >= :minFreightFee ");
			parameters.put("minFreightFee", minFreightFee);
		}

		if (Objects.nonNull(maxFreightFee)) {
			jpql.append(" AND  r.freightFee <= :maxFreightFee ");
			parameters.put("maxFreightFee", maxFreightFee);
		}

		TypedQuery<RestaurantListDto> query = entityManager.createQuery(jpql.toString(), RestaurantListDto.class);

		parameters.forEach(query::setParameter);

		return query.getResultList();
	}

	@Override
	public List<Restaurant> filterEntityByFreightFee(String name, Double minFreightFee, Double maxFreightFee) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteria.from(Restaurant.class);
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(builder.like(root.get(Restaurant_.name), "%" + name + "%"));
		predicates.add(builder.isFalse(root.get(Restaurant_.excluded)));
		if (Objects.nonNull(minFreightFee)) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Restaurant_.freightFee), minFreightFee));
		}
		if (Objects.nonNull(maxFreightFee)) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Restaurant_.freightFee), maxFreightFee));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurant> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

}
