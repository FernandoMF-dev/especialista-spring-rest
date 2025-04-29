package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.City;
import br.com.colatina.fmf.algafood.service.domain.model.City_;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CityMapper;
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
class CityControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_CITY = "/api/v1/cities";

	@Autowired
	private CityFactory cityFactory;
	@Autowired
	private CityMapper cityMapper;

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
		CityDto dto = cityMapper.toDto(entity);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + City_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + City_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void insert_fail_unauthorized() throws Exception {
		City entity = cityFactory.createEntity();
		CityDto dto = cityMapper.toDto(entity);

		getMockMvc().perform(post(API_CITY)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_CITY", "SCOPE_WRITE"})
	void update_success() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityDto dto = cityMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + City_.ID, Matchers.equalTo(dto.getId().intValue())))
				.andExpect(jsonPath("$." + City_.NAME, Matchers.equalTo(dto.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void update_fail_unauthorized() throws Exception {
		City entity = cityFactory.createAndPersist();
		CityDto dto = cityMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_CITY.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
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
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ", "SCOPE_WRITE"})
	void delete_fail_unauthorized() throws Exception {
		City entity = cityFactory.createAndPersist();

		getMockMvc().perform(delete(API_CITY.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}
}
