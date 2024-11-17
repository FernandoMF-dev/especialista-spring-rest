package br.com.colatina.fmf.algafood.service.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface Public {
		// No security check
	}

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

		@PreAuthorize("(hasAuthority('UPDATE_RESTAURANT') or hasAuthority('ADMINISTRATOR') or @appSecurity.managesRestaurant(#id)) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('OPEN_RESTAURANT') or hasAuthority('ADMINISTRATOR') or @appSecurity.managesRestaurant(#id))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Open {
		}

		@PreAuthorize("(hasAuthority('ACTIVATE_RESTAURANT') or hasAuthority('ADMINISTRATOR') or @appSecurity.managesRestaurant(#id)) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Activate {
		}

		@PreAuthorize("(hasAuthority('DELETE_RESTAURANT') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}

		@interface Responsible {
			@PreAuthorize("hasAuthority('SCOPE_READ') and " +
					" (hasAuthority('READ_RESTAURANT_RESPONSIBLE') or hasAuthority('ADMINISTRATOR') " +
					" or @appSecurity.managesRestaurant(#restaurantId))")
			@Retention(RetentionPolicy.RUNTIME)
			@Target(ElementType.METHOD)
			@interface Read {
			}

			@PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
					" (hasAuthority('ASSOCIATE_RESTAURANT_RESPONSIBLE') or hasAuthority('ADMINISTRATOR') " +
					" or @appSecurity.managesRestaurant(#restaurantId))")
			@Retention(RetentionPolicy.RUNTIME)
			@Target(ElementType.METHOD)
			@interface Associate {
			}

			@PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
					" (hasAuthority('DISASSOCIATE_RESTAURANT_RESPONSIBLE') or hasAuthority('ADMINISTRATOR') " +
					" or (@appSecurity.managesRestaurant(#restaurantId) and @appSecurity.getUserId() != #responsibleId))")
			@Retention(RetentionPolicy.RUNTIME)
			@Target(ElementType.METHOD)
			@interface Disassociate {
			}
		}
	}

	@interface Order {
		@PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('READ_ORDER') or hasAuthority('ADMINISTRATOR'))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface ListAll {
		}

		@PreAuthorize("hasAuthority('SCOPE_READ') and " +
				" (hasAuthority('READ_ORDER') or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.getUserId() == #filter.customerId " +
				" or @appSecurity.managesRestaurant(#filter.restaurantId))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface List {
		}

		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@PostAuthorize("hasAuthority('READ_ORDER') or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.getUserId() == returnObject.body.customer.id " +
				" or @appSecurity.managesRestaurant(returnObject.body.restaurant.id)")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
				" (hasAuthority('MANAGE_ORDER') " +
				" or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.managesOrderRestaurant(#orderUuid))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Manage {
		}
	}

	@interface PaymentMethod {
		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("(hasAuthority('CREATE_PAYMENT_METHOD') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("(hasAuthority('UPDATE_PAYMENT_METHOD') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('DELETE_PAYMENT_METHOD') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}
	}

	@interface State {
		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("(hasAuthority('CREATE_STATE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("(hasAuthority('UPDATE_STATE') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('DELETE_STATE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}
	}

	@interface City {
		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("(hasAuthority('CREATE_CITY') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("(hasAuthority('UPDATE_CITY') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('DELETE_CITY') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}
	}

	@interface User {
		@PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('READ_USER') or hasAuthority('ADMINISTRATOR'))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface List {
		}

		@PreAuthorize("hasAuthority('SCOPE_READ') and " +
				" (hasAuthority('READ_USER') or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.getUserId() == #id)")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
				" (hasAuthority('UPDATE_USER') or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.getUserId() == #id)")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("hasAuthority('SCOPE_DELETE') and " +
				" (hasAuthority('DELETE_USER') or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.getUserId() == #id)")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
				" (hasAuthority('CHANGE_USER_PASSWORD') or hasAuthority('ADMINISTRATOR') " +
				" or @appSecurity.getUserId() == #id)")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface ChangePassword {
		}
	}

	@interface Profile {
		@PreAuthorize("(hasAuthority('READ_PROFILE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_READ')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Read {
		}

		@PreAuthorize("(hasAuthority('CREATE_PROFILE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Create {
		}

		@PreAuthorize("(hasAuthority('UPDATE_PROFILE') or hasAuthority('ADMINISTRATOR'))  and hasAuthority('SCOPE_WRITE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Update {
		}

		@PreAuthorize("(hasAuthority('DELETE_PROFILE') or hasAuthority('ADMINISTRATOR')) and hasAuthority('SCOPE_DELETE')")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Delete {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('ASSOCIATE_PROFILE_PERMISSION') or hasAuthority('ADMINISTRATOR'))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface AssociatePermission {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('DISASSOCIATE_PROFILE_PERMISSION') or hasAuthority('ADMINISTRATOR'))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface DisassociatePermission {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('ASSOCIATE_USER_PROFILE') or hasAuthority('ADMINISTRATOR'))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface AssociateUser {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('DISASSOCIATE_USER_PROFILE') or hasAuthority('ADMINISTRATOR'))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface DisassociateUser {
		}
	}

	@interface Statistics {
		@PreAuthorize("hasAuthority('SCOPE_READ') and " +
				" (hasAuthority('EMIT_SALES_REPORT') or hasAuthority('ADMINISTRATOR') or " +
				" @appSecurity.managesRestaurant(#filter.restaurantId))")
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface EmitSalesReport {
		}
	}
}
