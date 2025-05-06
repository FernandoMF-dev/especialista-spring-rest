package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.model.Product_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.service.ProductCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProductMapper;
import br.com.colatina.fmf.algafood.service.factory.ProductFactory;
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
class RestaurantProductControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_RESTAURANT_PRODUCT = "/api/v1/restaurants/{restaurantId}/products";

	@Autowired
	private ProductFactory productFactory;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductCrudService productCrudService;

	@Autowired
	private RestaurantFactory restaurantFactory;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		MvcResult result = getMockMvc().perform(get(API_RESTAURANT_PRODUCT, restaurantId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.products").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray products = JsonPath.parse(response).read("$._embedded.products");

		validateEntityPresenceInResponseList(products, product.getId());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_fail_nonExistentRestaurant() throws Exception {
		getMockMvc().perform(get(API_RESTAURANT_PRODUCT, NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT, restaurantId))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurantId, product.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Product_.ID, Matchers.equalTo(product.getId().intValue())))
				.andExpect(jsonPath("$." + Product_.NAME, Matchers.equalTo(product.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentProduct() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurant.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_deletedProduct() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		productCrudService.delete(restaurantId, product.getId());

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurantId, product.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentRestaurant() throws Exception {
		Product product = productFactory.createAndPersist();

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT.concat("/{productId}"), NON_EXISTENT_ID, product.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_productFromAnotherRestaurant() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		Product product = productFactory.createAndPersist();

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurant.getId(), product.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(get(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurantId, product.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + Product_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + Product_.NAME, Matchers.equalTo(product.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_fail_blankName() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_fail_nullDescription() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(null);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_fail_nullPrice() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);
		dto.setPrice(null);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_fail_negativePrice() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);
		dto.setPrice(-10.0);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_fail_nullActive() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);
		dto.setActive(null);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void insert_fail_nonExistentRestaurant() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, NON_EXISTENT_ID)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void insert_fail_unauthorized() throws Exception {
		Product product = productFactory.createEntity();
		ProductDto dto = productMapper.toDto(product);

		getMockMvc().perform(post(API_RESTAURANT_PRODUCT, dto.getRestaurantId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_success() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + Product_.ID, Matchers.equalTo(dto.getId().intValue())))
				.andExpect(jsonPath("$." + Product_.NAME, Matchers.equalTo(product.getName())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_blankName() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setName(BLANK_STRING);

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_nullDescription() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(null);

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_nullPrice() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setPrice(null);

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_negativePrice() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setPrice(-10.0);

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_nullActive() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setActive(null);

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_nonExistentRestaurant() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), NON_EXISTENT_ID, dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_nonExistentProduct() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), NON_EXISTENT_ID)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void update_fail_productFromAnotherRestaurant() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurant.getId(), product.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void update_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		ProductDto dto = productMapper.toDto(product);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_RESTAURANT_PRODUCT.concat("/{productId}"), dto.getRestaurantId(), dto.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT_PRODUCT", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(delete(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurantId, product.getId()))
				.andExpect(status().isNoContent());

		Product deleted = productFactory.getById(product.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT_PRODUCT", "SCOPE_DELETE"})
	void delete_fail_nonExistentRestaurant() throws Exception {
		Product product = productFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT_PRODUCT.concat("/{productId}"), NON_EXISTENT_ID, product.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT_PRODUCT", "SCOPE_DELETE"})
	void delete_fail_nonExistentProduct() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurant.getId(), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_RESTAURANT_PRODUCT", "SCOPE_DELETE"})
	void delete_fail_productFromAnotherRestaurant() throws Exception {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		Product product = productFactory.createAndPersist();

		getMockMvc().perform(delete(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurant.getId(), product.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void delete_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(delete(API_RESTAURANT_PRODUCT.concat("/{productId}"), restaurantId, product.getId()))
				.andExpect(status().isForbidden());
	}
}
