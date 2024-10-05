package br.com.colatina.fmf.algafood.service.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
				.oauth2ResourceServer()
				.opaqueToken()
		;
	}
}
