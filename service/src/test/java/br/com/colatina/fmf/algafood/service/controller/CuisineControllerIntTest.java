package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.model.Cuisine_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import br.com.colatina.fmf.algafood.service.factory.CuisineFactory;
import br.com.colatina.fmf.algafood.service.factory.RestaurantFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuisineControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_CUISINE = "/api/v1/cuisines";

	@Autowired
	private CuisineFactory cuisineFactory;
	@Autowired
	private CuisineMapper cuisineMapper;
	@Autowired
	private CuisineCrudService cuisineCrudService;

	@Autowired
	private RestaurantFactory restaurantFactory;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_CUISINE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.cuisines").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray cuisines = JsonPath.parse(response).read("$._embedded.cuisines");

		validateEntityPresenceInResponseList(cuisines, entity.getId());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_CUISINE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAllXml_success() throws Exception {
		cuisineFactory.createAndPersist();

		getMockMvc().perform(get(API_CUISINE)
						.accept(MediaType.APPLICATION_XML_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAllXml_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_CUISINE)
						.accept(MediaType.APPLICATION_XML_VALUE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		getMockMvc().perform(get(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Cuisine_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + Cuisine_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_CUISINE.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		cuisineCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		getMockMvc().perform(get(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findFirst_success() throws Exception {
		cuisineFactory.createAndPersist();

		getMockMvc().perform(get(API_CUISINE.concat("/first")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Cuisine_.ID, Matchers.notNullValue()));
	}

	@Test
	@WithMockUser(username = "tester")
	void findFirst_fail_unauthorized() throws Exception {
		cuisineFactory.createAndPersist();

		getMockMvc().perform(get(API_CUISINE.concat("/first")))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CUISINE", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);

		getMockMvc().perform(post(API_CUISINE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + Cuisine_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + Cuisine_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CUISINE", "SCOPE_WRITE"})
	void insert_fail_blankName() throws Exception {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(post(API_CUISINE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CUISINE", "SCOPE_WRITE"})
	void insert_fail_nullName() throws Exception {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(null);

		getMockMvc().perform(post(API_CUISINE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void insert_fail_unauthorized() throws Exception {
		Cuisine entity = cuisineFactory.createEntity();
		CuisineDto dto = cuisineMapper.toDto(entity);

		getMockMvc().perform(post(API_CUISINE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CUISINE", "SCOPE_WRITE"})
	void update_success() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_CUISINE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Cuisine_.ID, Matchers.equalTo(dto.getId().intValue())))
				.andExpect(jsonPath("$." + Cuisine_.NAME, Matchers.equalTo(dto.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CUISINE", "SCOPE_WRITE"})
	void update_fail_nonExistentEntity() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_CUISINE.concat("/{id}"), NON_EXISTENT_ID)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CUISINE", "SCOPE_WRITE"})
	void update_fail_deletedEntity() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		cuisineCrudService.delete(entity.getId());

		getMockMvc().perform(put(API_CUISINE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CUISINE", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(put(API_CUISINE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CUISINE", "SCOPE_WRITE"})
	void update_fail_nullName() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(null);

		getMockMvc().perform(put(API_CUISINE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void update_fail_unauthorized() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_CUISINE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CUISINE", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		getMockMvc().perform(delete(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist());

		Cuisine deleted = cuisineFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CUISINE", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_CUISINE.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CUISINE", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		cuisineCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CUISINE", "SCOPE_DELETE"})
	void delete_fail_inUse() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		Cuisine entity = restaurant.getCuisine();

		getMockMvc().perform(delete(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isConflict());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ", "SCOPE_WRITE"})
	void delete_fail_unauthorized() throws Exception {
		Cuisine entity = cuisineFactory.createAndPersist();

		getMockMvc().perform(delete(API_CUISINE.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}
}
