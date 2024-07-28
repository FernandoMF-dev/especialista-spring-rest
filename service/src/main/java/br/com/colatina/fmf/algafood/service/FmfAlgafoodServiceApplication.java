package br.com.colatina.fmf.algafood.service;

import br.com.colatina.fmf.algafood.service.infrastructure.repository.CustomJpaRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FmfAlgafoodServiceApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(FmfAlgafoodServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		log.info("SPRING active profile(s): {}", String.join(", ", env.getActiveProfiles()));
	}
}
