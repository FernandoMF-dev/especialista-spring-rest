package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantFormMapper;
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

import java.util.ArrayList;

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
	private RestaurantFormMapper restaurantFormMapper;
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
	public void findById_fail_nonExistentEntity() {
		given().accept(ContentType.JSON)
				.pathParam("id", NON_EXISTING_ID)
				.when().get("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void findById_fail_deletedEntity() {
		Restaurant entity = restaurantFactory.createAndPersist();

		restaurantCrudService.delete(entity.getId());

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().get("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void insert_success() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.CREATED.value())
				.body(Restaurant_.ID, Matchers.notNullValue())
				.body(Restaurant_.NAME, Matchers.equalTo(entity.getName()));
	}

	@Test
	public void insert_fail_blankName() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_nullFreightFee() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(null);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_negativeFreightFee() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(-dto.getFreightFee());

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_nullCuisineId() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(null);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_nonExistentCuisineId() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(NON_EXISTING_ID);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_emptyPaymentMethod() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setPaymentMethods(new ArrayList<>());

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void insert_fail_nonExistentPaymentMethod() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.getPaymentMethods().add(NON_EXISTING_ID);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(dto)
				.when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_success() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
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
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
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
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		restaurantCrudService.delete(entity.getId());

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", dto.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void update_fail_blankName() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_fail_nullFreightFee() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(null);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_fail_negativeFreightFee() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(-dto.getFreightFee());

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_fail_nullCuisineId() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(null);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_fail_nonExistentCuisineId() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(NON_EXISTING_ID);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void update_fail_nonExistentPaymentMethod() {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.getPaymentMethods().add(NON_EXISTING_ID);

		given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", entity.getId())
				.body(dto)
				.when().put("/{id}")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());
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

	@Test
	public void delete_fail_nonExistentEntity() {
		given().accept(ContentType.JSON)
				.pathParam("id", NON_EXISTING_ID)
				.when().delete("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void delete_fail_deletedEntity() {
		Restaurant entity = restaurantFactory.createAndPersist();

		restaurantCrudService.delete(entity.getId());

		given().accept(ContentType.JSON)
				.pathParam("id", entity.getId())
				.when().delete("/{id}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}
}
