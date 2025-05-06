package br.com.colatina.fmf.algafood.service.factory;

import java.util.Objects;

/**
 * Base class for creating entity factories for automated tests.
 *
 * @param <E> Entity type to be built
 */
public abstract class BaseEntityFactory<E> {
	/**
	 * This method must return an instance of the entity initialized with the default data for all tests.
	 *
	 * @return Built entity
	 */
	public abstract E createEntity();

	/**
	 * This method must persist in the database the entity received in the parameter <b>entity</b>
	 *
	 * @param entity Entity to be persisted
	 * @return Persisted entity
	 */
	protected abstract E persist(E entity);

	/**
	 * This method must return the entity corresponding to the parameter <b>id</b>
	 *
	 * @param id id of the entity
	 * @return Persisted entity. If not found, return <b>null</b>
	 */
	public abstract E getById(Long id);

	/**
	 * This method must return an instance of the entity initialized with the default data for all tests.
	 *
	 * @param customization Customization to be applied on the entity. This customization will be applied after the default data is set.
	 * @return Built entity
	 */
	public E createEntity(EntityCustomizer<E> customization) {
		final E entidade = createEntity();
		if (Objects.nonNull(customization)) {
			customization.execute(entidade);
		}
		return entidade;
	}

	/**
	 * Builds the entity, performing customizations if necessary, and persists it in the database
	 *
	 * @return Persisted entity
	 */
	public E createAndPersist() {
		return createAndPersist(null);
	}

	/**
	 * Builds the entity, performing customizations if necessary, and persists it in the database
	 *
	 * @param customization Customization to be applied on the entity. This customization will be applied after the default data is set before it is persisted.
	 * @return Persisted entity
	 */
	public E createAndPersist(EntityCustomizer<E> customization) {
		final E entidade = createEntity(customization);
		return persist(entidade);
	}

	/**
	 * Interface that defines a contract to allow customization of an entity at the time of its construction. For use in testing
	 *
	 * @param <E> Entity type to be customized
	 */
	public interface EntityCustomizer<E> {
		/**
		 * Apply entity customization
		 *
		 * @param entity Entity to be customized
		 */
		void execute(E entity);
	}
}
