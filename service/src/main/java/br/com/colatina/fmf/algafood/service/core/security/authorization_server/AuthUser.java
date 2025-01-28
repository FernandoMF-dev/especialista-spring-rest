package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User {
	@Serial
	private static final long serialVersionUID = 1L;

	private final Long userId;
	private final String fullName;

	public AuthUser(AppUser apiUser, Collection<? extends GrantedAuthority> authorities) {
		super(apiUser.getEmail(), apiUser.getPassword(), authorities);

		this.userId = apiUser.getId();
		this.fullName = apiUser.getName();
	}
}
