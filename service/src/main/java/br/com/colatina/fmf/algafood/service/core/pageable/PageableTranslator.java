package br.com.colatina.fmf.algafood.service.core.pageable;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class to translate the original Pageable object's sort property based on the DTO class that is returned.
 * It creates a new Pageable object with the sort fields updated based on the SortableField annotations in the DTO class.
 */
@UtilityClass
public class PageableTranslator {
	public static <T> Pageable translate(Pageable pageable, Class<T> clazz) {
		Map<String, String> fieldMapping = getFieldMapping(clazz);
		List<Sort.Order> orders = pageable.getSort().stream()
				.filter(order -> fieldMapping.containsKey(order.getProperty()))
				.map(order -> {
					String property = order.getProperty();
					String mappedProperty = fieldMapping.get(property);
					return new Sort.Order(order.getDirection(), mappedProperty);
				}).collect(Collectors.toList());

		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}

	private static <T> Map<String, String> getFieldMapping(Class<T> clazz) {
		return getFieldMapping(clazz, "", "");
	}

	private static <T> Map<String, String> getFieldMapping(Class<T> clazz, String fieldPrefix, String translationPrefix) {
		Map<String, String> fieldMapping = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) {
			SortableField annotation = field.getAnnotation(SortableField.class);
			if (Objects.nonNull(annotation)) {
				mapFieldTranslation(fieldPrefix, translationPrefix, field, annotation, fieldMapping);
			}
		}
		return fieldMapping;
	}

	private static void mapFieldTranslation(String fieldPrefix, String translationPrefix, Field field, SortableField annotation, Map<String, String> fieldMapping) {
		String fieldName = String.format("%s%s", fieldPrefix, field.getName());
		String translation = annotation.translation().isEmpty() ? field.getName() : annotation.translation();
		String mappedTranslation = String.format("%s%s", translationPrefix, translation);
		fieldMapping.put(fieldName, mappedTranslation);
		if (annotation.recursive()) {
			Map<String, String> nestedFieldMapping = getFieldMapping(field.getType(), fieldName + ".", mappedTranslation + ".");
			fieldMapping.putAll(nestedFieldMapping);
		}
	}
}
