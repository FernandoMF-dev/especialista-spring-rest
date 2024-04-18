package br.com.colatina.fmf.algafood.service.utils;

import io.restassured.RestAssured;
import org.junit.Before;

public abstract class BaseCommonIntTest {
	protected static final Long NON_EXISTING_ID = 0L;

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
}
