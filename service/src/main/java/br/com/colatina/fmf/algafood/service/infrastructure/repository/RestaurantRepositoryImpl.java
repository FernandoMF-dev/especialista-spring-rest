package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class RestaurantRepositoryImpl implements RestaurantRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Restaurant> findAll() {
		TypedQuery<Restaurant> query = entityManager.createQuery("FROM Restaurant r", Restaurant.class);
		return query.getResultList();
	}

	@Override
	public Restaurant save(Restaurant entity) {
		return entityManager.merge(entity);
	}

	@Override
	public Restaurant findById(Long id) {
		return entityManager.find(Restaurant.class, id);
	}

	@Override
	public void delete(Restaurant entity) {
		Restaurant restaurant = findById(entity.getId());
		entityManager.remove(restaurant);
	}
}
