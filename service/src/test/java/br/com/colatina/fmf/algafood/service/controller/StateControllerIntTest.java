package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.State;
import br.com.colatina.fmf.algafood.service.domain.model.State_;
import br.com.colatina.fmf.algafood.service.domain.service.StateCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.StateMapper;
import br.com.colatina.fmf.algafood.service.factory.StateFactory;
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
class StateControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_STATE = "/api/v1/states";

	@Autowired
	private StateFactory stateFactory;
	@Autowired
	private StateMapper stateMapper;
	@Autowired
	private StateCrudService stateCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success() throws Exception {
		State entity = stateFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_STATE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.states").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray states = JsonPath.parse(response).read("$._embedded.states");

		validateEntityPresenceInResponseList(states, entity.getId());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_STATE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success() throws Exception {
		State entity = stateFactory.createAndPersist();

		getMockMvc().perform(get(API_STATE.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + State_.ID, org.hamcrest.Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + State_.NAME, org.hamcrest.Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_STATE.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		State entity = stateFactory.createAndPersist();

		stateCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_STATE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		State entity = stateFactory.createAndPersist();

		getMockMvc().perform(get(API_STATE.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_STATE", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		State entity = stateFactory.createEntity();
		StateDto dto = stateMapper.toDto(entity);

		getMockMvc().perform(post(API_STATE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + State_.ID, org.hamcrest.Matchers.notNullValue()))
				.andExpect(jsonPath("$." + State_.NAME, org.hamcrest.Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_STATE", "SCOPE_WRITE"})
	void insert_fail_blankAcronym() throws Exception {
		State entity = stateFactory.createEntity();
		StateDto dto = stateMapper.toDto(entity);
		dto.setAcronym(BLANK_STRING);

		getMockMvc().perform(post(API_STATE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_STATE", "SCOPE_WRITE"})
	void insert_fail_blankName() throws Exception {
		State entity = stateFactory.createEntity();
		StateDto dto = stateMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(post(API_STATE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void insert_fail_unauthorized() throws Exception {
		State entity = stateFactory.createEntity();
		StateDto dto = stateMapper.toDto(entity);

		getMockMvc().perform(post(API_STATE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_STATE", "SCOPE_WRITE"})
	void update_success() throws Exception {
		State entity = stateFactory.createAndPersist();
		StateDto dto = stateMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_STATE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + State_.ID, org.hamcrest.Matchers.equalTo(dto.getId().intValue())))
				.andExpect(jsonPath("$." + State_.NAME, Matchers.equalTo(dto.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_STATE", "SCOPE_WRITE"})
	void update_fail_blankAcronym() throws Exception {
		State entity = stateFactory.createAndPersist();
		StateDto dto = stateMapper.toDto(entity);
		dto.setAcronym(BLANK_STRING);

		getMockMvc().perform(put(API_STATE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_STATE", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		State entity = stateFactory.createAndPersist();
		StateDto dto = stateMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(put(API_STATE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void update_fail_unauthorized() throws Exception {
		State entity = stateFactory.createAndPersist();
		StateDto dto = stateMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_STATE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_STATE", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		State entity = stateFactory.createAndPersist();

		getMockMvc().perform(delete(API_STATE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist());

		State deleted = stateFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_STATE", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_STATE.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_STATE", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		State entity = stateFactory.createAndPersist();

		stateCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_STATE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ", "SCOPE_WRITE"})
	void delete_fail_unauthorized() throws Exception {
		State entity = stateFactory.createAndPersist();

		getMockMvc().perform(delete(API_STATE.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}
}
