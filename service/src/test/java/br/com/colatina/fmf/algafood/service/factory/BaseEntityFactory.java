package br.com.colatina.fmf.algafood.service.factory;


/**
 * Base class for creating entity factories for automated tests.
 *
 * @param <E> Entity type to be built
 */
public abstract class BaseEntityFactory<E> {

	private EntityCustomizer<E> customization;

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
	 * @return Persisted entity. If not found, returns <b>null</b>
	 */
	public abstract E getById(Long id);

	/**
	 * Builds the entity, performing customizations if necessary, and persists it in the database
	 *
	 * @return Persisted entity
	 */
	public E createAndPersist() {
		final E entidade = createEntity();
		if (isCustomized()) {
			customization.execute(entidade);
			removeCustomization();
		}
		return persist(entidade);
	}

	/**
	 * Builds the entity, performing customizations if necessary
	 *
	 * @return Built entity
	 */
	public E createCustomEntity() {
		final E entidade = createEntity();
		if (isCustomized()) {
			customization.execute(entidade);
			removeCustomization();
		}
		return entidade;
	}

	private boolean isCustomized() {
		return this.customization != null;
	}

	private void removeCustomization() {
		this.customization = null;
	}

	/**
	 * This method allows customization of the entity attributes before its persistence
	 *
	 * @param customization Customization to be applied on the entity
	 * @return Customized entity
	 */
	public BaseEntityFactory<E> customize(EntityCustomizer<E> customization) {
		this.customization = customization;
		return this;
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
