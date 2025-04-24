package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public abstract class BaseCommonControllerIntTest extends BaseCommonIntTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	protected MockMvc getMockMvc() {
		return mockMvc;
	}

	protected MultiValueMap<String, String> convertObjectToParams(Object obj) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		Map<String, String> maps = MAPPER.convertValue(obj, new TypeReference<>() {
		});

		params.setAll(maps);
		return params;
	}

	protected MultiValueMap<String, String> convertPageableToParams(Pageable pageable) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		if (pageable.isPaged()) {
			params.add("page", String.valueOf(pageable.getPageNumber()));
			params.add("size", String.valueOf(pageable.getPageSize()));
			params.add("sort", pageable.getSort().toString().replaceAll(": ", ","));
		}

		return params;
	}
}
