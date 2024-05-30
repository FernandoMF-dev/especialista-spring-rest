package br.com.colatina.fmf.algafood.service.core.pageable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark the fields in the DTO that can be sorted.
 * It takes a single parameter which is the corresponding field name in the domain entity.
 * If the parameter is not present, the field name will be used.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortableField {
	String value() default "";
}
