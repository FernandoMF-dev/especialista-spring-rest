package br.com.colatina.fmf.algafood.service;

import br.com.colatina.fmf.algafood.service.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FmfAlgafoodServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmfAlgafoodServiceApplication.class, args);
	}

}
