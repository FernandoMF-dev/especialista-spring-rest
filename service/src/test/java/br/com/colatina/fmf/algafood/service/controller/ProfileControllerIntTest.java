package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.model.Profile_;
import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProfileMapper;
import br.com.colatina.fmf.algafood.service.factory.ProfileFactory;
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
class ProfileControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_PROFILE = "/api/v1/profiles";

	@Autowired
	private ProfileFactory profileFactory;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private ProfileCrudService profileCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_success() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_PROFILE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.profiles").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray profiles = JsonPath.parse(response).read("$._embedded.profiles");

		validateEntityPresenceInResponseList(profiles, entity.getId());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_PROFILE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findById_success() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		getMockMvc().perform(get(API_PROFILE.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Profile_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + Profile_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_PROFILE.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		profileCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_PROFILE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		getMockMvc().perform(get(API_PROFILE.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_PROFILE", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		Profile entity = profileFactory.createEntity();
		ProfileDto dto = profileMapper.toDto(entity);

		getMockMvc().perform(post(API_PROFILE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + Profile_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + Profile_.NAME, Matchers.equalTo(entity.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_PROFILE", "SCOPE_WRITE"})
	void insert_fail_blankName() throws Exception {
		Profile entity = profileFactory.createEntity();
		ProfileDto dto = profileMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(post(API_PROFILE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void insert_fail_unauthorized() throws Exception {
		Profile entity = profileFactory.createEntity();
		ProfileDto dto = profileMapper.toDto(entity);

		getMockMvc().perform(post(API_PROFILE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_PROFILE", "SCOPE_WRITE"})
	void update_success() throws Exception {
		Profile entity = profileFactory.createAndPersist();
		ProfileDto dto = profileMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_PROFILE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Profile_.ID, Matchers.equalTo(dto.getId().intValue())))
				.andExpect(jsonPath("$." + Profile_.NAME, Matchers.equalTo(dto.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_PROFILE", "SCOPE_WRITE"})
	void update_fail_nonExistentEntity() throws Exception {
		Profile entity = profileFactory.createAndPersist();
		ProfileDto dto = profileMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_PROFILE.concat("/{id}"), NON_EXISTENT_ID)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_PROFILE", "SCOPE_WRITE"})
	void update_fail_deletedEntity() throws Exception {
		Profile entity = profileFactory.createAndPersist();
		ProfileDto dto = profileMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		profileCrudService.delete(entity.getId());

		getMockMvc().perform(put(API_PROFILE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_PROFILE", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		Profile entity = profileFactory.createAndPersist();
		ProfileDto dto = profileMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(put(API_PROFILE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void update_fail_unauthorized() throws Exception {
		Profile entity = profileFactory.createAndPersist();
		ProfileDto dto = profileMapper.toDto(entity);
		dto.setName(dto.getName() + " update");

		getMockMvc().perform(put(API_PROFILE.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_PROFILE", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		getMockMvc().perform(delete(API_PROFILE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist());

		Profile deleted = profileFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_PROFILE", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_PROFILE.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_PROFILE", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		profileCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_PROFILE.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void delete_fail_unauthorized() throws Exception {
		Profile entity = profileFactory.createAndPersist();

		getMockMvc().perform(delete(API_PROFILE.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}
}
