package br.com.colatina.fmf.algafood.service.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableMethodSecurity()
@EnableWebSecurity
public class ResourceServerConfig {
	@Bean
	public SecurityFilterChain resourceServerFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin(Customizer.withDefaults())
				.csrf().disable()
				.cors().and()
				.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

		return httpSecurity.build();
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			List<String> authorities = jwt.getClaimAsStringList("authorities");

			if (Objects.isNull(authorities)) {
				return Collections.emptyList();
			}

			JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
			Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

			grantedAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new).toList());

			return grantedAuthorities;
		});

		return converter;
	}
}
