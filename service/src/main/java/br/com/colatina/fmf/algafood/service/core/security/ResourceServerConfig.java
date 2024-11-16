package br.com.colatina.fmf.algafood.service.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/", "api", "api/v1", "api/v2", "swagger-ui/index.html#", "v3/api-docs").permitAll()
				.anyRequest().authenticated()
				.and()
				.cors()
				.and()
				.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter())
		;
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			var authorities = jwt.getClaimAsStringList("authorities");

			if (Objects.isNull(authorities)) {
				authorities = Collections.emptyList();
			}

			return authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		});

		return jwtAuthenticationConverter;
	}
}
