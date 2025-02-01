package br.com.colatina.fmf.algafood.service.infrastructure.specification;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Objects;

public abstract class BaseSpecs<E> {
	protected Class<E> entityClass;

	protected BaseSpecs(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@NonNull
	@SafeVarargs
	public final Specification<E> composedAnd(Specification<E>... specifications) {
		return (root, query, criteriaBuilder) -> {
			var predicates = new ArrayList<Predicate>();

			for (Specification<E> spec : specifications) {
				predicates.add(spec.toPredicate(root, query, criteriaBuilder));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	protected void fetch(Root<E> root, CriteriaQuery<?> query, SingularAttribute<E, ?> attribute) {
		if (entityClass.equals(query.getResultType())) {
			root.fetch(attribute);
		}
	}

	protected Predicate defaultReturn(CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.and();
	}

	protected Predicate compareString(CriteriaBuilder criteriaBuilder, Path<String> rootPath, String value) {
		if (Strings.isNotEmpty(value)) {
			return criteriaBuilder.like(criteriaBuilder.upper(rootPath), "%" + value.toUpperCase() + "%");
		}

		return defaultReturn(criteriaBuilder);
	}

	protected Predicate compareBoolean(CriteriaBuilder criteriaBuilder, Path<Boolean> rootPath, Boolean value) {
		if (Objects.nonNull(value)) {
			return criteriaBuilder.equal(rootPath, value);
		}

		return defaultReturn(criteriaBuilder);
	}
}
