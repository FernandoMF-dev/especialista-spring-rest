package br.com.colatina.fmf.algafood.service.core.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(apiInfo())
				.externalDocs(externalDocs());
	}

	private Info apiInfo() {
		return new Info()
				.title("Algafood API")
				.description("Open API for customers and restaurants")
				.version("1.0.0")
				.license(license());
	}

	private License license() {
		return new License()
				.name("Apache 2.0")
				.url("https://springdoc.com");
	}

	private ExternalDocumentation externalDocs() {
		return new ExternalDocumentation()
				.description("Algafood Docs")
				.url("https://algafood-fmf.com/docs");
	}
}
