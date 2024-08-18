package br.com.colatina.fmf.algafood.service.api.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class EntityHateoas<M extends RepresentationModel<M>> {
	private final Class<M> clazz;
	private final Map<Class<?>, EntityHateoas<?>> subEntityHateoas;

	protected EntityHateoas(Class<M> clazz, EntityHateoas<?>... subEntityHateoas) {
		this.clazz = clazz;
		this.subEntityHateoas = new HashMap<>();
		for (EntityHateoas<?> subHateoas : subEntityHateoas) {
			this.subEntityHateoas.put(subHateoas.getModelClass(), subHateoas);
		}
	}

	protected abstract void addModelHypermediaLinks(M model);

	protected abstract void addCollectionHypermediaLinks(CollectionModel<M> collection);

	public final Class<M> getModelClass() {
		return clazz;
	}

	public final CollectionModel<M> mapCollectionModel(Iterable<M> models) {
		models.forEach(this::mapModel);
		CollectionModel<M> collection = CollectionModel.of(models);
		addCollectionHypermediaLinks(collection);
		return collection;
	}

	public final void mapModel(M model) {
		addModelHypermediaLinks(model);
		mapSubModels(model);
	}

	public final <L extends Iterable<M>> void mapModel(L models) {
		models.forEach(this::mapModel);
	}

	private void mapSubModels(RepresentationModel<M> model) {
		for (Field field : model.getClass().getDeclaredFields()) {
			try {
				var getterMethod = getGetter(model, field);
				Object fieldValue = getterMethod.invoke(model);
				if (fieldValue instanceof RepresentationModel) {
					mapModelProperties(fieldValue);
				} else if (fieldValue instanceof Iterable) {
					mapListProperties((Iterable<?>) fieldValue);
				}
			} catch (NoSuchMethodException e) {
				// Ignore fields without a getter method
			} catch (Exception e) {
				throw new RuntimeException("Failed to access field: " + field.getName(), e);
			}
		}
	}

	private void mapListProperties(Iterable<?> fieldValue) {
		for (Object item : fieldValue) {
			if (item instanceof RepresentationModel) {
				mapModelProperties(item);
			}
		}
	}

	private <T extends RepresentationModel<T>> void mapModelProperties(Object fieldValue) {
		if (subEntityHateoas.containsKey(fieldValue.getClass())) {
			EntityHateoas<T> subEntity = (EntityHateoas<T>) subEntityHateoas.get(fieldValue.getClass());
			subEntity.mapModel((T) fieldValue);
		}
	}

	private Method getGetter(RepresentationModel<M> model, Field field) throws NoSuchMethodException {
		String getterName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		return model.getClass().getMethod(getterName);
	}
}
