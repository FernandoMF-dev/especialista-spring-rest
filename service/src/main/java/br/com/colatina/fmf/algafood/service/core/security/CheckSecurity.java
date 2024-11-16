package br.com.colatina.fmf.algafood.service.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {
	@PreAuthorize("isAuthenticated()")
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface Public {
	}

	@interface Cuisine {
		@PreAuthorize("hasAuthority('CREATE_CUISINE') or hasAuthority('ADMINISTRATOR')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("hasAuthority('UPDATE_CUISINE') or hasAuthority('ADMINISTRATOR')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("hasAuthority('DELETE_CUISINE') or hasAuthority('ADMINISTRATOR')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}
	}
}
