package br.com.colatina.fmf.algafood.auth.domain;

import br.com.colatina.fmf.algafood.auth.core.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApiUser apiUser = userRepository.findByEmailAndExcludedIsFalse(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail informado"));

		return new AuthUser(apiUser, getAuthorities(apiUser));
	}

	private Collection<GrantedAuthority> getAuthorities(ApiUser apiUser) {
		return apiUser.getProfiles().stream()
				.filter(profile -> !profile.getExcluded())
				.flatMap(profile -> profile.getPermissions().stream())
				.filter(permission -> !permission.getExcluded())
				.map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
				.collect(Collectors.toSet());
	}
}
