package br.com.colatina.fmf.algafood.service.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(CorsConfiguration.ALL) // Habilita CORS para todas as URLs da aplicação
				.allowedMethods("*") // Habilita todos os métodos HTTP (get, post, put, delete, etc)
				.maxAge(30L * 60L); // Determina o tempo (em segundos) que o navegador deve armazenar a configuração CORS em cache
	}
}
