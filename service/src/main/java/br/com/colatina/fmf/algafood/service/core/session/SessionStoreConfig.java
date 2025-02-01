package br.com.colatina.fmf.algafood.service.core.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;

import java.util.HashMap;

@Configuration
public class SessionStoreConfig {
	@Bean
	@ConditionalOnProperty(
			value = "spring.session.store-type",
			havingValue = "none",
			matchIfMissing = true)
	public SessionRepository<?> sessionRepository() {
		return new MapSessionRepository(new HashMap<>());
	}
}
