package br.com.colatina.fmf.algafood.service.jpa;

import br.com.colatina.fmf.algafood.service.domain.entity.Kitchen;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class KitchenRegister {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Kitchen> findAll() {
		TypedQuery<Kitchen> query = entityManager.createQuery("FROM Kitchen k", Kitchen.class);
		return query.getResultList();
	}
}
