package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.model.ProductPicture;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public ProductPicture save(ProductPicture productPicture) {
		return entityManager.merge(productPicture);
	}

	@Override
	@Transactional
	public void delete(ProductPicture productPicture) {
		entityManager.remove(productPicture);
	}
}
