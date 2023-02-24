package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.repository.queries.RestaurantRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RestaurantDto> filterByFreightRate(Double minFreightRate, Double maxFreightRate) {
		var jpql = "SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto" +
				"(r.id, r.name, r.freightRate, r.registrationDate, r.updateDate, r.active, k.id) " +
				" FROM Restaurant r LEFT JOIN r.kitchen k " +
				" WHERE r.excluded = FALSE " +
				" AND r.freightRate BETWEEN :minFreightRate AND :maxFreightRate ";

		return entityManager.createQuery(jpql, RestaurantDto.class)
				.setParameter("minFreightRate", minFreightRate)
				.setParameter("maxFreightRate", maxFreightRate)
				.getResultList();
	}

}
