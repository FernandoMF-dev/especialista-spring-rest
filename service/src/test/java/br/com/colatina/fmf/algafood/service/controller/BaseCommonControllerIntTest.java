package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCommonControllerIntTest extends BaseCommonIntTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Before
	public abstract void setUpConnection();

	protected MockMvc getMockMvc() {
		return mockMvc;
	}

	protected Map<String, Object> convertObjectToParams(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(obj, new TypeReference<>() {
		});
	}

	protected Map<String, Object> convertPageableToParams(Pageable pageable) {
		Map<String, Object> params = new HashMap<>();

		if (pageable.isPaged()) {
			params.put("page", pageable.getPageNumber());
			params.put("size", pageable.getPageSize());
			params.put("sort", pageable.getSort().toString().replaceAll(": ", ","));
		}

		return params;
	}
}
