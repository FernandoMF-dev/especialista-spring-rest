package br.com.colatina.fmf.algafood.service.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {
	@interface Cuisine {
		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("(hasAuthority('CREATE_CUISINE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("(hasAuthority('UPDATE_CUISINE') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('DELETE_CUISINE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}
	}

	@interface Restaurant {
		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("(hasAuthority('CREATE_RESTAURANT') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("(hasAuthority('UPDATE_RESTAURANT') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('OPEN_RESTAURANT') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Open {
		}

		@PreAuthorize("(hasAuthority('ACTIVATE_RESTAURANT') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Activate {
		}

		@PreAuthorize("(hasAuthority('DELETE_RESTAURANT') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}
	}
}
