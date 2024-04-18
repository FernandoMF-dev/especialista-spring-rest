package br.com.colatina.fmf.algafood.service.container;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainersFactory {
	private static PostgreSQLContainer postgre;

	public static void startContainers() {
		if (Objects.isNull(postgre)) {
			postgre = PostgreSqlContainerFactory.getInstance();
		}
	}
}
