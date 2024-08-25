package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.domain.service.dto.GenericObjectDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
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
		CollectionModel<M> collection = CollectionModel.of(this.mapModel(models));
		addCollectionHypermediaLinks(collection);
		return collection;
	}

	public final PagedModel<M> mapPagedModel(Page<M> models) {
		List<M> content = this.mapModel(models.getContent());
		var metadata = new PagedModel.PageMetadata(models.getSize(), models.getNumber(), models.getTotalElements(), models.getTotalPages());
		PagedModel<M> pagedModel = PagedModel.of(content, metadata);
		addCollectionHypermediaLinks(pagedModel);
		return pagedModel;
	}

	public final M mapModel(M model) {
		addModelHypermediaLinks(model);
		mapSubModels(model);
		return model;
	}

	public final <L extends Iterable<M>> L mapModel(L models) {
		models.forEach(this::mapModel);
		return models;
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

	protected void mapGenericModel(GenericObjectDto model) {
		// Optional to be overridden
	}

	protected Link getPageLink(URI uri) {
		TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM)
		);

		UriTemplate url = UriTemplate.of(uri.toString(), pageVariables);

		String relation = "page";

		return Link.of(url, relation);
	}
}
