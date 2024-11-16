package br.com.colatina.fmf.algafood.auth.core;

import br.com.colatina.fmf.algafood.auth.domain.ApiUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User {
	private static final long serialVersionUID = 1L;

	private final Long userId;
	private final String fullName;

	public AuthUser(ApiUser apiUser, Collection<? extends GrantedAuthority> authorities) {
		super(apiUser.getEmail(), apiUser.getPassword(), authorities);

		this.userId = apiUser.getId();
		this.fullName = apiUser.getName();
	}
}