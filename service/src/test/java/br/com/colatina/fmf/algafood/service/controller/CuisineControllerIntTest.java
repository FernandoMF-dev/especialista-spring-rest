package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.model.Cuisine_;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import br.com.colatina.fmf.algafood.service.factory.CuisineFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
public class CuisineControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_CUISINE = "/api/cuisines";

	@LocalServerPort
	protected Integer serverPort;

	@Autowired
	private CuisineFactory cuisineFactory;
	@Autowired
	private CuisineMapper cuisineMapper;
	@Autowired
	private CuisineCrudService cuisineCrudService;

	@Override
	public void setUpConnection() {
		RestAssured.basePath = API_CUISINE;
		RestAssured.port = serverPort;
	}

	@Test
	public void findAll_success() {
		Cuisine entity = cuisineFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.when().get()
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()))
				.body(Cuisine_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body(Cuisine_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void findAllXml_success() {
		cuisineFactory.createAndPersist();

		given().accept(ContentType.XML)
				.when().get()
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()));
	}

	@Test
	public void findById_success() {
		Cuisine entity = cuisineFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().get("/{id}")
				.then().statusCode(HttpStatus.OK.value())
				.body(Cuisine_.ID, Matchers.equalTo(entity.getId().intValue()))
				.body(Cuisine_.NAME, Matchers.equalTo(entity.getName()));
	}

	@Test
	public void findById_fail_nonExistentEntity() {
		given().accept(ContentType.JSON)
				.pathParam("id", NON_EXISTING_ID)
				.when().get("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void findById_fail_deletedEntity() {
		Cuisine entity = cuisineFactory.createAndPersist();

		cuisineCrudService.delete(entity.getId());

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().get("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void findFirst_success() {
		cuisineFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.when().get("first")
				.then().statusCode(HttpStatus.OK.value())
				.body(Cuisine_.ID, Matchers.notNullValue());
	}

	@Test
	public void insert_success() {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.CREATED.value())
				.body(Cuisine_.ID, Matchers.notNullValue())
				.body(Cuisine_.NAME, Matchers.equalTo(entity.getName()));
	}

	@Test
	public void insert_fail_blankName() {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_nullName() {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(null);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_success() {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.OK.value())
				.body(Cuisine_.ID, Matchers.equalTo(entity.getId().intValue()))
				.body(Cuisine_.NAME, Matchers.equalTo(dto.getName()));
	}

	@Test
	public void update_fail_nonExistentEntity() {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", NON_EXISTING_ID)
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void update_fail_deletedEntity() {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		cuisineCrudService.delete(entity.getId());

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", dto.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void update_fail_blankName() {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_fail_nullName() {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(null);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void delete_success() {
		Cuisine entity = cuisineFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().delete("/{id}")
				.then().statusCode(HttpStatus.NO_CONTENT.value())
				.body(Matchers.emptyOrNullString());

		Cuisine deleted = cuisineFactory.getById(entity.getId());
		Assert.assertTrue(deleted.getExcluded());
	}

	@Test
	public void delete_fail_nonExistentEntity() {
		given().accept(ContentType.JSON)
				.pathParam("id", NON_EXISTING_ID)
				.when().delete("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void delete_fail_deletedEntity() {
		Cuisine entity = cuisineFactory.createAndPersist();

		cuisineCrudService.delete(entity.getId());

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().delete("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}
}
