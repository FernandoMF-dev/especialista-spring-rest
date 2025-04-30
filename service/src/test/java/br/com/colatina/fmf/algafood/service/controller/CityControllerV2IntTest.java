package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.api.v2.model.input.CityInputV2;
import br.com.colatina.fmf.algafood.service.domain.model.City;
import br.com.colatina.fmf.algafood.service.domain.model.City_;
import br.com.colatina.fmf.algafood.service.domain.service.CityCrudService;
import br.com.colatina.fmf.algafood.service.factory.CityFactory;
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
public class CityControllerV2IntTest extends BaseCommonControllerIntTest {
	private static final String API_CITY = "/api/v2/cities";

	@Autowired
	private CityFactory cityFactory;
	@Autowired
	private CityCrudService cityCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success() throws Exception {
		City entity = cityFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_CITY))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.cities").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray cities = JsonPath.parse(response).read("$._embedded.cities");

		validateEntityPresenceInResponseList(cities, entity.getId());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_CITY))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success() throws Exception {
		City entity = cityFactory.createAndPersist();

		getMockMvc().perform(get(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + City_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + City_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_CITY.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		City entity = cityFactory.createAndPersist();

		cityCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		City entity = cityFactory.createAndPersist();

		getMockMvc().perform(get(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CITY", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		City entity = cityFactory.createEntity();
		CityInputV2 input = mapToInput(entity);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + City_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + City_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CITY", "SCOPE_WRITE"})
	void insert_fail_nullAcronym() throws Exception {
		City entity = cityFactory.createEntity();
		CityInputV2 input = mapToInput(entity);
		input.setAcronym(null);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CITY", "SCOPE_WRITE"})
	void insert_fail_blankName() throws Exception {
		City entity = cityFactory.createEntity();
		CityInputV2 input = mapToInput(entity);
		input.setName(BLANK_STRING);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CITY", "SCOPE_WRITE"})
	void insert_fail_nullState() throws Exception {
		City entity = cityFactory.createEntity();
		CityInputV2 input = mapToInput(entity);
		input.setStateId(null);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_CITY", "SCOPE_WRITE"})
	void insert_fail_nonExistentState() throws Exception {
		City entity = cityFactory.createEntity();
		CityInputV2 input = mapToInput(entity);
		input.setStateId(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void insert_fail_unauthorized() throws Exception {
		City entity = cityFactory.createEntity();
		CityInputV2 input = mapToInput(entity);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CITY", "SCOPE_WRITE"})
	void update_success() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityInputV2 input = mapToInput(entity);
		input.setName(input.getName() + " update");

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + City_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + City_.NAME, Matchers.equalTo(input.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CITY", "SCOPE_WRITE"})
	void update_fail_blankAcronym() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityInputV2 input = mapToInput(entity);
		input.setAcronym(BLANK_STRING);

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CITY", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityInputV2 input = mapToInput(entity);
		input.setName(BLANK_STRING);

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CITY", "SCOPE_WRITE"})
	void update_fail_nullState() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityInputV2 input = mapToInput(entity);
		input.setStateId(null);

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CITY", "SCOPE_WRITE"})
	void update_fail_nonExistentState() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityInputV2 input = mapToInput(entity);
		input.setStateId(NON_EXISTENT_ID);

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void update_fail_unauthorized() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityInputV2 input = mapToInput(entity);
		input.setName(input.getName() + " update");

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CITY", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		City entity = cityFactory.createAndPersist();

		getMockMvc().perform(delete(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist());

		City deleted = cityFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CITY", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_CITY.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_CITY", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		City entity = cityFactory.createAndPersist();

		cityCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ", "SCOPE_WRITE"})
	void delete_fail_unauthorized() throws Exception {
		City entity = cityFactory.createAndPersist();

		getMockMvc().perform(delete(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	private CityInputV2 mapToInput(City entity) {
		CityInputV2 input = new CityInputV2();

		input.setAcronym(entity.getAcronym());
		input.setName(entity.getName());
		input.setStateId(entity.getState().getId());

		return input;
	}
}
