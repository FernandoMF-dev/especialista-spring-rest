package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.model.AppUser_;
import br.com.colatina.fmf.algafood.service.domain.service.AppUserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.AppUserMapper;
import br.com.colatina.fmf.algafood.service.factory.AppUserFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_USER = "/api/v1/users";

	@Autowired
	private AppUserFactory appUserFactory;
	@Autowired
	private AppUserMapper appUserMapper;
	@Autowired
	private AppUserCrudService appUserCrudService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_USER", "SCOPE_READ"})
	void findAll_success() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_USER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.users").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray users = JsonPath.parse(response).read("$._embedded.users");

		validateEntityPresenceInResponseList(users, entity.getId());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_USER))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_USER", "SCOPE_READ"})
	void findById_success() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		getMockMvc().perform(get(API_USER.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + AppUser_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + AppUser_.EMAIL, Matchers.equalTo(entity.getEmail())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_USER", "SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_USER.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_USER", "SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		appUserCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_USER.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		getMockMvc().perform(get(API_USER.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	void insert_success() throws Exception {
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + AppUser_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + AppUser_.EMAIL, Matchers.equalTo(entity.getEmail())));
	}

	@Test
	void insert_fail_blankName() throws Exception {
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void insert_fail_blankEmail() throws Exception {
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);
		dto.setEmail(BLANK_STRING);

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void insert_fail_invalidEmail() throws Exception {
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);
		dto.setEmail("invalid_email");

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void insert_fail_duplicatedEmail() throws Exception {
		AppUser saved = appUserFactory.createAndPersist();
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);
		dto.setEmail(saved.getEmail());

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void insert_fail_blankPassword() throws Exception {
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);
		dto.setPassword(BLANK_STRING);

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void insert_fail_invalidPassword() throws Exception {
		AppUser entity = appUserFactory.createEntity();
		AppUserInsertDto dto = appUserFactory.mapInsertDto(entity);
		dto.setPassword(AppUserFactory.MOCK_INVALID_PASSWORD);

		getMockMvc().perform(post(API_USER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_USER", "SCOPE_WRITE"})
	void update_success() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		AppUserDto dto = appUserMapper.toDto(entity);
		dto.setName(dto.getName() + " Update");

		getMockMvc().perform(put(API_USER.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + AppUser_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + AppUser_.EMAIL, Matchers.equalTo(dto.getEmail())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_USER", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		AppUserDto dto = appUserMapper.toDto(entity);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(put(API_USER.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_USER", "SCOPE_WRITE"})
	void update_fail_blankEmail() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		AppUserDto dto = appUserMapper.toDto(entity);
		dto.setEmail(BLANK_STRING);

		getMockMvc().perform(put(API_USER.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_USER", "SCOPE_WRITE"})
	void update_fail_invalidEmail() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		AppUserDto dto = appUserMapper.toDto(entity);
		dto.setEmail("invalid_email");

		getMockMvc().perform(put(API_USER.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_USER", "SCOPE_WRITE"})
	void update_fail_duplicatedEmail() throws Exception {
		AppUser saved = appUserFactory.createAndPersist();
		AppUser entity = appUserFactory.createAndPersist();
		AppUserDto dto = appUserMapper.toDto(entity);
		dto.setEmail(saved.getEmail());

		getMockMvc().perform(put(API_USER.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void update_fail_unauthorized() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		AppUserDto dto = appUserMapper.toDto(entity);
		dto.setName(dto.getName() + " Update");

		getMockMvc().perform(put(API_USER.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CHANGE_USER_PASSWORD", "SCOPE_WRITE"})
	void changePassword_success() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		PasswordChangeDto dto = new PasswordChangeDto(AppUserFactory.MOCK_VALID_PASSWORD, "lJ05h2SVa2$@");

		getMockMvc().perform(patch(API_USER.concat("/{id}/password"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNoContent());

		AppUser updatedEntity = appUserFactory.getById(entity.getId());
		Assertions.assertTrue(passwordEncoder.matches(dto.getNewPassword(), updatedEntity.getPassword()));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CHANGE_USER_PASSWORD", "SCOPE_WRITE"})
	void changePassword_fail_blankCurrentPassword() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		PasswordChangeDto dto = new PasswordChangeDto(BLANK_STRING, "lJ05h2SVa2$@");

		getMockMvc().perform(patch(API_USER.concat("/{id}/password"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CHANGE_USER_PASSWORD", "SCOPE_WRITE"})
	void changePassword_fail_incorrectCurrentPassword() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		PasswordChangeDto dto = new PasswordChangeDto("KC!8V=1]y3z,", "lJ05h2SVa2$@");

		getMockMvc().perform(patch(API_USER.concat("/{id}/password"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CHANGE_USER_PASSWORD", "SCOPE_WRITE"})
	void changePassword_fail_blankNewPassword() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		PasswordChangeDto dto = new PasswordChangeDto(entity.getPassword(), BLANK_STRING);

		getMockMvc().perform(patch(API_USER.concat("/{id}/password"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CHANGE_USER_PASSWORD", "SCOPE_WRITE"})
	void changePassword_fail_invalidNewPassword() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		PasswordChangeDto dto = new PasswordChangeDto(entity.getPassword(), AppUserFactory.MOCK_INVALID_PASSWORD);

		getMockMvc().perform(patch(API_USER.concat("/{id}/password"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void changePassword_fail_unauthorized() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();
		PasswordChangeDto dto = new PasswordChangeDto(entity.getPassword(), "lJ05h2SVa2$@");

		getMockMvc().perform(patch(API_USER.concat("/{id}/password"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_USER", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		getMockMvc().perform(delete(API_USER.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent());

		AppUser deleted = appUserFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_USER", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_USER.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_USER", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		appUserCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_USER.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void delete_fail_unauthorized() throws Exception {
		AppUser entity = appUserFactory.createAndPersist();

		getMockMvc().perform(delete(API_USER.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}
}
