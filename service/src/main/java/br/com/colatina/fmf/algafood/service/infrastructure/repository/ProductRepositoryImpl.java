package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.model.ProductPicture;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
