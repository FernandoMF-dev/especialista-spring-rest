package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.repository.queries.RestaurantRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RestaurantDto> filterByFreightRate(Double minFreightRate, Double maxFreightRate) {
		StringBuilder jpql = new StringBuilder("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto" +
				"(r.id, r.name, r.freightRate, r.registrationDate, r.updateDate, r.active, k.id) " +
				" FROM Restaurant r LEFT JOIN r.kitchen k " +
				" WHERE r.excluded = FALSE ");

		HashMap<String, Object> parameters = new HashMap<>();

		if (Objects.nonNull(minFreightRate)) {
			jpql.append(" AND r.freightRate > :minFreightRate ");
			parameters.put("minFreightRate", minFreightRate);
		}

		if (Objects.nonNull(maxFreightRate)) {
			jpql.append(" AND  r.freightRate < :maxFreightRate ");
			parameters.put("maxFreightRate", maxFreightRate);
		}

		TypedQuery<RestaurantDto> query = entityManager.createQuery(jpql.toString(), RestaurantDto.class);

		parameters.forEach(query::setParameter);

		return query.getResultList();
	}

}
