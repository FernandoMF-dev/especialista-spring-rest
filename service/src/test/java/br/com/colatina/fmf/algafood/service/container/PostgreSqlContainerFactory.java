package br.com.colatina.fmf.algafood.service.container;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostgreSqlContainerFactory {
	private static final String DOCKER_IMAGE_NAME = PostgreSQLContainer.IMAGE + ":14.1-alpine";

	private static PostgreSQLContainer container;

	public static PostgreSQLContainer getInstance() {
		if (Objects.isNull(container)) {
			container = new PostgreSQLContainer(DOCKER_IMAGE_NAME)
					.withDatabaseName("db_database_test")
					.withUsername("db_user")
					.withPassword("db_password");

			container.start();
		}
		return container;
	}
}
