package br.com.colatina.fmf.algafood.service.repository.impl;

import br.com.colatina.fmf.algafood.service.domain.entity.Kitchen;
import br.com.colatina.fmf.algafood.service.repository.KitchenRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class KitchenRepositoryImpl implements KitchenRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Kitchen> findAll() {
		TypedQuery<Kitchen> query = entityManager.createQuery("FROM Kitchen k", Kitchen.class);
		return query.getResultList();
	}

	@Override
	public Kitchen save(Kitchen entity) {
		return entityManager.merge(entity);
	}

	@Override
	public Kitchen findById(Long id) {
		return entityManager.find(Kitchen.class, id);
	}

	@Override
	public void delete(Kitchen entity) {
		Kitchen kitchen = findById(entity.getId());
		entityManager.remove(kitchen);
	}
}
