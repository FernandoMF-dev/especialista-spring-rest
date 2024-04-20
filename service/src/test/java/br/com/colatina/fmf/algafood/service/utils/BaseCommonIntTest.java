package br.com.colatina.fmf.algafood.service.utils;

import br.com.colatina.fmf.algafood.service.container.ContainersFactory;
import br.com.colatina.fmf.algafood.service.container.PostgreSqlContainerFactory;
import io.restassured.RestAssured;
import org.junit.Before;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@ContextConfiguration(initializers = {BaseCommonIntTest.Initializer.class})
public abstract class BaseCommonIntTest {
	protected static final Long NON_EXISTING_ID = 0L;
	protected static final String BLANK_STRING = "";

	static {
		ContainersFactory.startContainers();
	}

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		private static final String SEPARATOR = "=";

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url" + SEPARATOR + PostgreSqlContainerFactory.getInstance().getJdbcUrl(),
					"spring.datasource.username" + SEPARATOR + PostgreSqlContainerFactory.getInstance().getUsername(),
					"spring.datasource.password" + SEPARATOR + PostgreSqlContainerFactory.getInstance().getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}
