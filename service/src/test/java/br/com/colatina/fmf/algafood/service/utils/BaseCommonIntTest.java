package br.com.colatina.fmf.algafood.service.utils;

import br.com.colatina.fmf.algafood.service.container.ContainersFactory;
import br.com.colatina.fmf.algafood.service.container.PostgreSqlContainerFactory;
import br.com.colatina.fmf.algafood.service.core.io.Base64ProtocolResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@ActiveProfiles("test")
@ContextConfiguration(initializers = {BaseCommonIntTest.Initializer.class})
public abstract class BaseCommonIntTest {
	protected static final ObjectMapper MAPPER = createObjectMapper();
	protected static final Long NON_EXISTENT_ID = 0L;
	protected static final String BLANK_STRING = "";

	static {
		ContainersFactory.startContainers();
	}

	protected static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		return MAPPER.writeValueAsBytes(object);
	}

	private static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		private static final String SEPARATOR = "=";

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			configurableApplicationContext.addProtocolResolver(new Base64ProtocolResolver());

			TestPropertyValues.of(
					"spring.datasource.url" + SEPARATOR + PostgreSqlContainerFactory.getInstance().getJdbcUrl(),
					"spring.datasource.username" + SEPARATOR + PostgreSqlContainerFactory.getInstance().getUsername(),
					"spring.datasource.password" + SEPARATOR + PostgreSqlContainerFactory.getInstance().getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}
