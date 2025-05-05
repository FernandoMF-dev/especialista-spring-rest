package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.service.AppUserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.factory.AppUserFactory;
import br.com.colatina.fmf.algafood.service.factory.ProfileFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserProfileControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_USER_PROFILE = "/api/v1/users/{userId}/profiles";

	@Autowired
	private AppUserFactory appUserFactory;
	@Autowired
	private ProfileFactory profileFactory;
	@Autowired
	private AppUserCrudService appUserCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_success() throws Exception {
		AppUser user = appUserFactory.createAndPersist();
		Profile profile = profileFactory.createAndPersist();
		appUserCrudService.addProfileToUser(user.getId(), profile.getId());

		MvcResult result = getMockMvc().perform(get(API_USER_PROFILE, user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.profiles").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray profiles = JsonPath.parse(response).read("$._embedded.profiles");

		validateEntityPresenceInResponseList(profiles, profile.getId());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_fail_nonExistentUser() throws Exception {
		getMockMvc().perform(get(API_USER_PROFILE, NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_fail_deletedUser() throws Exception {
		AppUser user = appUserFactory.createAndPersist();

		appUserCrudService.delete(user.getId());

		getMockMvc().perform(get(API_USER_PROFILE, user.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		AppUser user = appUserFactory.createAndPersist();

		getMockMvc().perform(get(API_USER_PROFILE, user.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_USER_PROFILE", "SCOPE_WRITE"})
	void associate_success() throws Exception {
		AppUser user = appUserFactory.createAndPersist();
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(put(API_USER_PROFILE.concat("/{profileId}"), user.getId(), profile.getId()))
				.andExpect(status().isNoContent());

		Set<ProfileDto> profiles = appUserCrudService.findAllProfilesByUser(user.getId());
		Assertions.assertTrue(profiles.stream().anyMatch(dto -> Objects.equals(dto.getId(), profile.getId())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_USER_PROFILE", "SCOPE_WRITE"})
	void associate_fail_nonExistentUser() throws Exception {
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(put(API_USER_PROFILE.concat("/{profileId}"), NON_EXISTENT_ID, profile.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_USER_PROFILE", "SCOPE_WRITE"})
	void associate_fail_nonExistentProfile() throws Exception {
		AppUser user = appUserFactory.createAndPersist();

		getMockMvc().perform(put(API_USER_PROFILE.concat("/{profileId}"), user.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void associate_fail_unauthorized() throws Exception {
		AppUser user = appUserFactory.createAndPersist();
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(put(API_USER_PROFILE.concat("/{profileId}"), user.getId(), profile.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_USER_PROFILE", "SCOPE_WRITE"})
	void disassociate_success() throws Exception {
		AppUser user = appUserFactory.createAndPersist();
		Profile profile = profileFactory.createAndPersist();
		appUserCrudService.addProfileToUser(user.getId(), profile.getId());

		getMockMvc().perform(delete(API_USER_PROFILE.concat("/{profileId}"), user.getId(), profile.getId()))
				.andExpect(status().isNoContent());

		Set<ProfileDto> profiles = appUserCrudService.findAllProfilesByUser(user.getId());
		Assertions.assertFalse(profiles.stream().anyMatch(dto -> Objects.equals(dto.getId(), profile.getId())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_USER_PROFILE", "SCOPE_WRITE"})
	void disassociate_fail_nonExistentUser() throws Exception {
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(delete(API_USER_PROFILE.concat("/{profileId}"), NON_EXISTENT_ID, profile.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_USER_PROFILE", "SCOPE_WRITE"})
	void disassociate_fail_nonExistentProfile() throws Exception {
		AppUser user = appUserFactory.createAndPersist();

		getMockMvc().perform(delete(API_USER_PROFILE.concat("/{profileId}"), user.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void disassociate_fail_unauthorized() throws Exception {
		AppUser user = appUserFactory.createAndPersist();
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(delete(API_USER_PROFILE.concat("/{profileId}"), user.getId(), profile.getId()))
				.andExpect(status().isForbidden());
	}
}
