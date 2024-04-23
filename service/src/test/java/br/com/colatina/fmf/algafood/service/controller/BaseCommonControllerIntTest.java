package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.Before;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCommonControllerIntTest extends BaseCommonIntTest {
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Before
	public abstract void setUpConnection();

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
