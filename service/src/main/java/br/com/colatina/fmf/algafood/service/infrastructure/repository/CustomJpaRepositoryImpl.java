package br.com.colatina.fmf.algafood.service.infrastructure.repository;

import br.com.colatina.fmf.algafood.service.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {
	private final EntityManager entityManager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public Optional<T> findFirst() {
		var jpql = "FROM " + getDomainClass().getName();

		try {
			Field excludedField = getDomainClass().getDeclaredField("excluded");
			jpql += " WHERE " + excludedField.getName() + " = FALSE";
		} catch (NoSuchFieldException ignored) { /* Ignored exception */}

		List<T> entityList = entityManager.createQuery(jpql, getDomainClass())
				.setMaxResults(1)
				.getResultList();

		if (entityList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(entityList.get(0));
	}

	@Override
	public void detach(T entity) {
		entityManager.detach(entity);
	}
}
