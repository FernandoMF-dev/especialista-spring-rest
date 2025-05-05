package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Permission;
import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.repository.PermissionRepository;
import br.com.colatina.fmf.algafood.service.domain.service.PermissionCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import br.com.colatina.fmf.algafood.service.factory.ProfileFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class ProfilePermissionControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_PROFILE_PERMISSION = "/api/v1/profiles/{profileId}/permissions";
	private static final Long PERMISSION_ID = 1L;

	@Autowired
	private ProfileFactory profileFactory;
	@Autowired
	private ProfileCrudService profileCrudService;
	@Autowired
	private PermissionCrudService permissionCrudService;
	@Autowired
	private PermissionRepository permissionRepository;

	@BeforeEach
	void assurePermissionsExists() {
		if (permissionRepository.count() > 0) {
			return;
		}

		// For some reason I'm unaware of, the changelog "20241103144000_insert_permission.xml" was not working as intended.
		// No error was thrown or printed in the terminal, and no problem occurred when I run the API.
		// But when I run the tests, it seems the data on the CSV file read by the "loadData" method simply doesn't load on the database.
		// Maybe the database is being reset before the tests, or maybe I'm missing some configuration.
		// So I'm creating the permission manually here to ensure the tests run correctly, in case this happens.
		Permission permission = new Permission();
		permission.setId(PERMISSION_ID);
		permission.setName("ADMINISTRATOR");
		permission.setExcluded(Boolean.FALSE);
		permissionRepository.save(permission);
		permissionRepository.flush();
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_success() throws Exception {
		Profile profile = profileFactory.createAndPersist();
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);
		profileCrudService.addPermissionToProfile(profile.getId(), permission.getId());

		MvcResult result = getMockMvc().perform(get(API_PROFILE_PERMISSION, profile.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.permissions").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray permissions = JsonPath.parse(response).read("$._embedded.permissions");

		validateEntityPresenceInResponseList(permissions, permission.getId());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_fail_nonExistentProfile() throws Exception {
		getMockMvc().perform(get(API_PROFILE_PERMISSION, NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_PROFILE", "SCOPE_READ"})
	void findAll_fail_deletedProfile() throws Exception {
		Profile profile = profileFactory.createAndPersist();

		profileCrudService.delete(profile.getId());

		getMockMvc().perform(get(API_PROFILE_PERMISSION, profile.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(get(API_PROFILE_PERMISSION, profile.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_PROFILE_PERMISSION", "SCOPE_WRITE"})
	void associate_success() throws Exception {
		Profile profile = profileFactory.createAndPersist();
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);

		getMockMvc().perform(put(API_PROFILE_PERMISSION.concat("/{permissionId}"), profile.getId(), permission.getId()))
				.andExpect(status().isNoContent());

		Set<PermissionDto> permissions = profileCrudService.findAllPermissionsByProfile(profile.getId());
		Assertions.assertTrue(permissions.stream().anyMatch(dto -> Objects.equals(dto.getId(), permission.getId())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_PROFILE_PERMISSION", "SCOPE_WRITE"})
	void associate_fail_nonExistentProfile() throws Exception {
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);

		getMockMvc().perform(put(API_PROFILE_PERMISSION.concat("/{permissionId}"), NON_EXISTENT_ID, permission.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_PROFILE_PERMISSION", "SCOPE_WRITE"})
	void associate_fail_nonExistentPermission() throws Exception {
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(put(API_PROFILE_PERMISSION.concat("/{permissionId}"), profile.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void associate_fail_unauthorized() throws Exception {
		Profile profile = profileFactory.createAndPersist();
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);

		getMockMvc().perform(put(API_PROFILE_PERMISSION.concat("/{permissionId}"), profile.getId(), permission.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_PROFILE_PERMISSION", "SCOPE_WRITE"})
	void disassociate_success() throws Exception {
		Profile profile = profileFactory.createAndPersist();
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);
		profileCrudService.addPermissionToProfile(profile.getId(), permission.getId());

		getMockMvc().perform(delete(API_PROFILE_PERMISSION.concat("/{permissionId}"), profile.getId(), permission.getId()))
				.andExpect(status().isNoContent());

		Set<PermissionDto> permissions = profileCrudService.findAllPermissionsByProfile(profile.getId());
		Assertions.assertFalse(permissions.stream().anyMatch(dto -> Objects.equals(dto.getId(), permission.getId())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_PROFILE_PERMISSION", "SCOPE_WRITE"})
	void disassociate_fail_nonExistentProfile() throws Exception {
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);

		getMockMvc().perform(delete(API_PROFILE_PERMISSION.concat("/{permissionId}"), NON_EXISTENT_ID, permission.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_PROFILE_PERMISSION", "SCOPE_WRITE"})
	void disassociate_fail_nonExistentPermission() throws Exception {
		Profile profile = profileFactory.createAndPersist();

		getMockMvc().perform(delete(API_PROFILE_PERMISSION.concat("/{permissionId}"), profile.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void disassociate_fail_unauthorized() throws Exception {
		Profile profile = profileFactory.createAndPersist();
		Permission permission = permissionCrudService.findEntityById(PERMISSION_ID);

		getMockMvc().perform(delete(API_PROFILE_PERMISSION.concat("/{permissionId}"), profile.getId(), permission.getId()))
				.andExpect(status().isForbidden());
	}
}
