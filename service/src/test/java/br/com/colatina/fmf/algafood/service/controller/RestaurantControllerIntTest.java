package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantFormMapper;
import br.com.colatina.fmf.algafood.service.factory.RestaurantFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestaurantControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_RESTAURANT = "/api/v1/restaurants";
	public static final double FREIGHT_FEE_DIFFERENCE = 1.1;

	@Autowired
	private RestaurantFactory restaurantFactory;
	@Autowired
	private RestaurantFormMapper restaurantFormMapper;
	@Autowired
	private RestaurantCrudService restaurantCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.restaurants").isArray())
				.andReturn();

		validateEntityPresenceInResponseList(result, entity);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void filterByFreightFee_success_noFilter() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT.concat("/freight-fee")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.restaurants").isArray())
				.andReturn();

		validateEntityPresenceInResponseList(result, entity);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void filterByFreightFee_success_noName() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT.concat("/freight-fee"))
						.queryParam("min", String.valueOf(entity.getFreightFee() - FREIGHT_FEE_DIFFERENCE))
						.queryParam("max", String.valueOf(entity.getFreightFee() + FREIGHT_FEE_DIFFERENCE)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.restaurants").isArray())
				.andReturn();

		validateEntityPresenceInResponseList(result, entity);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void filterByFreightFee_success_withName() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT.concat("/freight-fee"))
						.queryParam("name", entity.getName())
						.queryParam("min", String.valueOf(entity.getFreightFee() - FREIGHT_FEE_DIFFERENCE))
						.queryParam("max", String.valueOf(entity.getFreightFee() + FREIGHT_FEE_DIFFERENCE)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.restaurants").isArray())
				.andReturn();

		validateEntityPresenceInResponseList(result, entity);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void page_success_noFilterAndPaged() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		Pageable pageable = Pageable.unpaged();
		RestaurantPageFilter filter = new RestaurantPageFilter();

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT.concat("/page"))
						.queryParams(convertPageableToParams(pageable))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.restaurants").isArray())
				.andExpect(jsonPath("$.page.totalElements", Matchers.greaterThanOrEqualTo(1)))
				.andReturn();

		validateEntityPresenceInResponseList(result, entity);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void page_success_withFilterAndPage() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, Restaurant_.NAME);
		RestaurantPageFilter filter = new RestaurantPageFilter();

		filter.setName(entity.getName());
		filter.setActive(entity.getActive());
		filter.setCuisineId(entity.getCuisine().getId());
		filter.setMinFreightFee(entity.getFreightFee() - FREIGHT_FEE_DIFFERENCE);
		filter.setMaxFreightFee(entity.getFreightFee() + FREIGHT_FEE_DIFFERENCE);

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT.concat("/page"))
						.queryParams(convertPageableToParams(pageable))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.restaurants").isArray())
				.andExpect(jsonPath("$.page.totalElements", Matchers.greaterThanOrEqualTo(1)))
				.andReturn();

		validateEntityPresenceInResponseList(result, entity);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findFirst_success() throws Exception {
		restaurantFactory.createAndPersist();

		getMockMvc().perform(get(API_RESTAURANT.concat("/first")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Restaurant_.ID, Matchers.notNullValue()));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		getMockMvc().perform(get(API_RESTAURANT.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Restaurant_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + Restaurant_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_RESTAURANT.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		restaurantCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_RESTAURANT.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + Restaurant_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + Restaurant_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_blankName() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_nullFreightFee() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(null);

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_negativeFreightFee() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(-dto.getFreightFee());

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_nullCuisineId() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(null);

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_nonExistentCuisineId() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_emptyPaymentMethod() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setPaymentMethods(new HashSet<>());

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT", "SCOPE_WRITE"})
	void insert_fail_nonExistentPaymentMethod() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.getPaymentMethods().add(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_RESTAURANT)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_success() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Restaurant_.ID, Matchers.equalTo(dto.getId().intValue())))
				.andExpect(jsonPath("$." + Restaurant_.NAME, Matchers.equalTo(dto.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_nonExistentEntity() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), NON_EXISTENT_ID)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_deletedEntity() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		restaurantCrudService.delete(entity.getId());

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_nullFreightFee() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(null);

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_negativeFreightFee() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setFreightFee(-dto.getFreightFee());

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_nullCuisineId() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(null);

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_nonExistentCuisineId() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.setCuisineId(NON_EXISTENT_ID);

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT", "SCOPE_WRITE"})
	void update_fail_nonExistentPaymentMethod() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();
		RestaurantFormDto dto = restaurantFormMapper.toDto(entity);
		dto.getPaymentMethods().add(NON_EXISTENT_ID);

		getMockMvc().perform(put(API_RESTAURANT.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist());

		Restaurant deleted = restaurantFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_RESTAURANT.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		Restaurant entity = restaurantFactory.createAndPersist();

		restaurantCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_RESTAURANT.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	private void validateEntityPresenceInResponseList(MvcResult result, Restaurant entity) throws UnsupportedEncodingException {
		String response = result.getResponse().getContentAsString();
		JSONArray restaurants = JsonPath.parse(response).read("$._embedded.restaurants");

		Assertions.assertTrue(restaurants.stream().anyMatch(element -> {
			if (element instanceof LinkedHashMap) {
				return ((LinkedHashMap<?, ?>) element).get(Restaurant_.ID).equals(entity.getId().intValue());
			}
			return false;
		}));
	}
}
