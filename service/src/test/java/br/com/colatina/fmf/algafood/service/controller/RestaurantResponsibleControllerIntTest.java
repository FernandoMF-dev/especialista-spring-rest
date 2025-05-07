package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.factory.AppUserFactory;
import br.com.colatina.fmf.algafood.service.factory.RestaurantFactory;
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
class RestaurantResponsibleControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_RESTAURANT_RESPONSIBLE = "/api/v1/restaurants/{restaurantId}/responsible";

	@Autowired
	private RestaurantFactory restaurantFactory;
	@Autowired
	private AppUserFactory appUserFactory;
	@Autowired
	private RestaurantCrudService restaurantCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_RESTAURANT_RESPONSIBLE", "SCOPE_READ"})
	void findAll_success() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();
		restaurantCrudService.addResponsibleToRestaurant(restaurant.getId(), responsible.getId());

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT_RESPONSIBLE, restaurant.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.users").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray users = JsonPath.parse(response).read("$._embedded.users");

		validateEntityPresenceInResponseList(users, responsible.getId());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_RESTAURANT_RESPONSIBLE", "SCOPE_READ"})
	void findAll_fail_nonExistentRestaurant() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();
		restaurantCrudService.addResponsibleToRestaurant(restaurant.getId(), responsible.getId());

		getMockMvc().perform(get(API_RESTAURANT_RESPONSIBLE, NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();
		restaurantCrudService.addResponsibleToRestaurant(restaurant.getId(), responsible.getId());

		getMockMvc().perform(get(API_RESTAURANT_RESPONSIBLE, NON_EXISTENT_ID))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_RESTAURANT_RESPONSIBLE", "SCOPE_WRITE"})
	void associate_success() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();

		getMockMvc().perform(put(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), restaurant.getId(), responsible.getId()))
				.andExpect(status().isNoContent());

		Set<AppUserDto> responsibles = restaurantCrudService.findAllResponsiblesByRestaurant(restaurant.getId());
		Assertions.assertTrue(responsibles.stream().anyMatch(element -> Objects.equals(element.getId(), responsible.getId())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_RESTAURANT_RESPONSIBLE", "SCOPE_WRITE"})
	void associate_fail_nonExistentRestaurant() throws Exception {
		AppUser responsible = appUserFactory.createAndPersist();

		getMockMvc().perform(put(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), NON_EXISTENT_ID, responsible.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"ASSOCIATE_RESTAURANT_RESPONSIBLE", "SCOPE_WRITE"})
	void associate_fail_nonExistentUser() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();

		getMockMvc().perform(put(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), restaurant.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void associate_fail_unauthorized() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();

		getMockMvc().perform(put(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), restaurant.getId(), responsible.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_RESTAURANT_RESPONSIBLE", "SCOPE_WRITE"})
	void disassociate_success() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();
		restaurantCrudService.addResponsibleToRestaurant(restaurant.getId(), responsible.getId());

		getMockMvc().perform(delete(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), restaurant.getId(), responsible.getId()))
				.andExpect(status().isNoContent());

		Set<AppUserDto> responsibles = restaurantCrudService.findAllResponsiblesByRestaurant(restaurant.getId());
		Assertions.assertFalse(responsibles.stream().anyMatch(element -> Objects.equals(element.getId(), responsible.getId())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_RESTAURANT_RESPONSIBLE", "SCOPE_WRITE"})
	void disassociate_fail_nonExistentRestaurant() throws Exception {
		AppUser responsible = appUserFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), NON_EXISTENT_ID, responsible.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DISASSOCIATE_RESTAURANT_RESPONSIBLE", "SCOPE_WRITE"})
	void disassociate_fail_nonExistentUser() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), restaurant.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void disassociate_fail_unauthorized() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		AppUser responsible = appUserFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT_RESPONSIBLE.concat("/{responsibleId}"), restaurant.getId(), responsible.getId()))
				.andExpect(status().isForbidden());
	}
}
