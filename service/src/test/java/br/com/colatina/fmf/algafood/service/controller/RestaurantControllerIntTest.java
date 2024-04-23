package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantMapper;
import br.com.colatina.fmf.algafood.service.factory.RestaurantFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestaurantControllerIntTest extends BaseCommonControllerIntTest {
	public static final double FREIGHT_FEE_DIFFERENCE = 1.1;
	private static final String API_RESTAURANT = "/api/restaurants";
	@LocalServerPort
	protected Integer serverPort;

	@Autowired
	private RestaurantFactory restaurantFactory;
	@Autowired
	private RestaurantMapper restaurantMapper;
	@Autowired
	private RestaurantCrudService restaurantCrudService;

	@Override
	public void setUpConnection() {
		RestAssured.basePath = API_RESTAURANT;
		RestAssured.port = serverPort;
	}

	@Test
	public void findAll_success() {
		Restaurant entity = restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.when().get()
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()))
				.body(Restaurant_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body(Restaurant_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void filterByFreightFee_success_noFilter() {
		Restaurant entity = restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.when().get("/freight-fee")
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()))
				.body(Restaurant_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body(Restaurant_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void filterByFreightFee_success_noName() {
		Restaurant entity = restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.queryParam("min", entity.getFreightFee() - FREIGHT_FEE_DIFFERENCE)
				.queryParam("max", entity.getFreightFee() + FREIGHT_FEE_DIFFERENCE)
				.when().get("/freight-fee")
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()))
				.body(Restaurant_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body(Restaurant_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void filterByFreightFee_success_withName() {
		Restaurant entity = restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.queryParam("name", entity.getName())
				.queryParam("min", entity.getFreightFee() - FREIGHT_FEE_DIFFERENCE)
				.queryParam("max", entity.getFreightFee() + FREIGHT_FEE_DIFFERENCE)
				.when().get("/freight-fee")
				.then().statusCode(HttpStatus.OK.value())
				.body(Matchers.not(Matchers.emptyIterable()))
				.body(Restaurant_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body(Restaurant_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void page_success_noFilterAndPaged() {
		Restaurant entity = restaurantFactory.createAndPersist();
		Pageable pageable = Pageable.unpaged();
		RestaurantPageFilter filter = new RestaurantPageFilter();

		given().accept(ContentType.JSON)
				.params(convertPageableToParams(pageable))
				.params(convertObjectToParams(filter))
				.when().get("/page")
				.then().statusCode(HttpStatus.OK.value())
				.body("content", Matchers.not(Matchers.emptyIterable()))
				.body("content." + Restaurant_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body("content." + Restaurant_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void page_success_withFilterAndPage() {
		Restaurant entity = restaurantFactory.createAndPersist();
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, Restaurant_.NAME);
		RestaurantPageFilter filter = new RestaurantPageFilter();

		filter.setName(entity.getName());
		filter.setActive(entity.getActive());
		filter.setCuisineId(entity.getCuisine().getId());
		filter.setMinFreightFee(entity.getFreightFee() - FREIGHT_FEE_DIFFERENCE);
		filter.setMaxFreightFee(entity.getFreightFee() + FREIGHT_FEE_DIFFERENCE);

		given().accept(ContentType.JSON)
				.params(convertPageableToParams(pageable))
				.params(convertObjectToParams(filter))
				.when().get("/page")
				.then().statusCode(HttpStatus.OK.value())
				.body("content", Matchers.not(Matchers.emptyIterable()))
				.body("content." + Restaurant_.ID, Matchers.hasItems(entity.getId().intValue()))
				.body("content." + Restaurant_.NAME, Matchers.hasItems(entity.getName()));
	}

	@Test
	public void findFirst_success() {
		restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.when().get("first")
				.then().statusCode(HttpStatus.OK.value())
				.body(Restaurant_.ID, Matchers.notNullValue());
	}

	@Test
	public void findById_success() {
		Restaurant entity = restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().get("/{id}")
				.then().statusCode(HttpStatus.OK.value())
				.body(Restaurant_.ID, Matchers.equalTo(entity.getId().intValue()))
				.body(Restaurant_.NAME, Matchers.equalTo(entity.getName()));
	}

	@Test
	public void insert_success() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantDto dto = restaurantMapper.toDto(entity);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.CREATED.value())
				.body(Restaurant_.ID, Matchers.notNullValue())
				.body(Restaurant_.NAME, Matchers.equalTo(entity.getName()));
	}

	@Test
	public void update_success() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantDto dto = restaurantMapper.toDto(entity);
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
	public void delete_success() {
		Restaurant entity = restaurantFactory.createAndPersist();

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().delete("/{id}")
				.then().statusCode(HttpStatus.NO_CONTENT.value())
				.body(Matchers.emptyOrNullString());

		Restaurant deleted = restaurantFactory.getById(entity.getId());
		Assert.assertTrue(deleted.getExcluded());
	}

}
