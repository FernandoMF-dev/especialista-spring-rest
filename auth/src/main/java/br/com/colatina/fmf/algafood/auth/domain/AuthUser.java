package br.com.colatina.fmf.algafood.auth.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User {
	private static final long serialVersionUID = 1L;

	private final String fullName;

	public AuthUser(ApiUser apiUser) {
		super(apiUser.getEmail(), apiUser.getPassword(), Collections.emptyList());

		this.fullName = apiUser.getName();
	}
}
