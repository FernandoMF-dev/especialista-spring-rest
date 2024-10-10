package br.com.colatina.fmf.algafood.auth.core;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

// Fonte: https://spring.io/blog/2015/06/08/cors-support-in-spring-framework#filter-based-cors-support
@Configuration
public class CorsConfig {
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/oauth/token", config);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}
}

/*
Nota sobre o allowCredentials - Algaworks

A configuração de allowCredentials como "true", combinada com allowedOrigins como "*", não é suportada pelos navegadores.
Recomendamos utilizar a opção allowCredentials como false, caso o allowedOrigins seja "*".
Só é possível utilizar a opção allowCredentials como "true", caso sejam especificadas as Origins permitidas.
* */
