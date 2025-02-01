package br.com.colatina.fmf.algafood.service.core.validation.constraints;

import br.com.colatina.fmf.algafood.service.core.validation.validators.PasswordConstraintValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface Password {
	String message() default "{Password.invalid}";

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * The minimum number of lowercase letters the password must have
	 */
	int lowerCase() default 1;

	/**
	 * The maximum length the password can be
	 */
	int maxLenght() default Integer.MAX_VALUE;

	/**
	 * The minimum length the password must be
	 */
	int minLenght() default 0;

	/**
	 * The minimum number of numeric characters the password must have
	 */
	int numerical() default 1;

	/**
	 * The minimum number of special characters the password must have
	 */
	int special() default 1;

	/**
	 * The minimum number of uppercase letters the password must have
	 */
	int upperCase() default 1;
}
