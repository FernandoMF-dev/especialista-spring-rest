package br.com.colatina.fmf.algafood.service;

import br.com.colatina.fmf.algafood.service.core.io.Base64ProtocolResolver;
import br.com.colatina.fmf.algafood.service.infrastructure.repository.CustomJpaRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FmfAlgafoodServiceApplication {
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		var app = new SpringApplication(FmfAlgafoodServiceApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
	}

	@PostConstruct
	public void init() {
		log.info("SPRING active profile(s): {}", String.join(", ", env.getActiveProfiles()));
	}
}
