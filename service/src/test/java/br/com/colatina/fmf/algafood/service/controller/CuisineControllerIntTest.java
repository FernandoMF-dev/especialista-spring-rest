package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.model.Cuisine_;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import br.com.colatina.fmf.algafood.service.factory.CuisineFactory;
import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CuisineControllerIntTest extends BaseCommonIntTest {
	private static final String API_CUISINE = "/api/cuisines";

	@LocalServerPort
	protected Integer serverPort;

	@Autowired
	private CuisineCrudService cuisineCrudService;
	@Autowired
	private CuisineFactory cuisineFactory;
	@Autowired
	private CuisineMapper cuisineMapper;

	@Before
	public void setUpConnection() {
		RestAssured.basePath = API_CUISINE;
		RestAssured.port = serverPort;
	}

	@Test
	public void findAll_success() {
		Cuisine cuisine = cuisineFactory.createAndPersist();

		given().basePath(API_CUISINE).accept(ContentType.JSON)
				.when().get()
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()))
				.body(Cuisine_.ID, Matchers.hasItems(cuisine.getId().intValue()))
				.body(Cuisine_.NAME, Matchers.hasItems(cuisine.getName()));
	}

	@Test
	public void findAllXml_success() {
		cuisineFactory.createAndPersist();

		given().basePath(API_CUISINE).accept(ContentType.XML)
				.when().get()
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()));
	}
}
