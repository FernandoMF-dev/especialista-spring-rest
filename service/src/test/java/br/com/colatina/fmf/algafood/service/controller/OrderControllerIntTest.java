package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.core.security.AppSecurity;
import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import br.com.colatina.fmf.algafood.service.domain.service.ProductCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.OrderPageFilter;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderInsertMapper;
import br.com.colatina.fmf.algafood.service.factory.OrderFactory;
import br.com.colatina.fmf.algafood.service.factory.PaymentMethodFactory;
import br.com.colatina.fmf.algafood.service.factory.ProductFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_ORDER = "/api/v1/orders";

	@Autowired
	private OrderFactory orderFactory;
	@Autowired
	private OrderInsertMapper orderInsertMapper;

	@Autowired
	private PaymentMethodFactory paymentMethodFactory;
	@Autowired
	private ProductFactory productFactory;
	@Autowired
	private RestaurantCrudService restaurantCrudService;
	@Autowired
	private ProductCrudService productCrudService;

	@MockBean
	private AppSecurity appSecurity;

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_ORDER", "SCOPE_READ"})
	void findAll_success() throws Exception {
		Order order = orderFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_ORDER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.orders").isArray())
				.andReturn();

		validateEntityPresenceInResponseList(result, order);
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_ORDER))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_ORDER", "SCOPE_READ"})
	void findByUuid_success() throws Exception {
		Order order = orderFactory.createAndPersist();

		getMockMvc().perform(get(API_ORDER.concat("/{uuid}"), order.getUuidCode()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", Matchers.equalTo(order.getUuidCode())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_ORDER", "SCOPE_READ"})
	void findByUuid_fail_nonExistentEntity() throws Exception {
		String uuid = UUID.randomUUID().toString();

		getMockMvc().perform(get(API_ORDER.concat("/{uuid}"), uuid))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findByUuid_fail_unauthorized() throws Exception {
		Order order = orderFactory.createAndPersist();

		getMockMvc().perform(get(API_ORDER.concat("/{uuid}"), order.getUuidCode()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_ORDER", "SCOPE_READ"})
	void page_success_noFilterAndUnpaged() throws Exception {
		Order order = orderFactory.createAndPersist();
		Pageable pageable = Pageable.unpaged();
		OrderPageFilter filter = new OrderPageFilter();

		MvcResult result = getMockMvc().perform(get(API_ORDER.concat("/page"))
						.queryParams(convertPageableToParams(pageable))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.orders").isArray())
				.andExpect(jsonPath("$.page.totalElements", Matchers.greaterThanOrEqualTo(1)))
				.andReturn();

		validateEntityPresenceInResponseList(result, order);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"READ_ORDER", "SCOPE_READ"})
	void page_success_withFilterAndPage() throws Exception {
		Order order = orderFactory.createAndPersist();
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, Restaurant_.NAME);
		OrderPageFilter filter = new OrderPageFilter();

		filter.setStatus(order.getStatus());
		filter.setRestaurantId(order.getRestaurant().getId());
		filter.setCustomerId(order.getCustomer().getId());
		filter.setMinRegistrationDate(OffsetDateTime.now().minusDays(5));
		filter.setMaxRegistrationDate(OffsetDateTime.now().plusDays(5));

		MvcResult result = getMockMvc().perform(get(API_ORDER.concat("/page"))
						.queryParams(convertPageableToParams(pageable))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.orders").isArray())
				.andExpect(jsonPath("$.page.totalElements", Matchers.greaterThanOrEqualTo(1)))
				.andReturn();

		validateEntityPresenceInResponseList(result, order);
	}

	@Test
	@WithMockUser(username = "tester")
	void page_fail_unauthorized() throws Exception {
		Pageable pageable = Pageable.unpaged();
		OrderPageFilter filter = new OrderPageFilter();

		getMockMvc().perform(get(API_ORDER.concat("/page"))
						.queryParams(convertPageableToParams(pageable))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_success() throws Exception {
		OrderInsertDto dto = createInsertDto();

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$.code", Matchers.notNullValue()))
				.andExpect(jsonPath("$.status", Matchers.equalTo(OrderStatusEnum.CREATED.name())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nonExistentCustomer() throws Exception {
		OrderInsertDto dto = createInsertDto();
		Mockito.doReturn(NON_EXISTENT_ID).when(appSecurity).getUserId();

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nullRestaurant() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.setRestaurantId(null);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nonExistentRestaurant() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.setRestaurantId(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_closedRestaurant() throws Exception {
		OrderInsertDto dto = createInsertDto();
		restaurantCrudService.toggleOpen(dto.getRestaurantId(), false);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_inactiveRestaurant() throws Exception {
		OrderInsertDto dto = createInsertDto();
		restaurantCrudService.toggleActive(dto.getRestaurantId(), false);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nullPaymentMethod() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.setPaymentMethodId(null);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nonExistentPaymentMethod() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.setPaymentMethodId(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_unacceptedPaymentMethod() throws Exception {
		OrderInsertDto dto = createInsertDto();
		PaymentMethod paymentMethod = paymentMethodFactory.createAndPersist();
		dto.setPaymentMethodId(paymentMethod.getId());

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nullAddress() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.setAddress(null);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_blankAddressCep() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().setCep(BLANK_STRING);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_invalidAddressCep() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().setCep("Invalid CEP");

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_blankAddressPublicSpace() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().setPublicSpace(BLANK_STRING);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_blankAddressStreetNumber() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().setStreetNumber(BLANK_STRING);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_blankAddressDistrict() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().setDistrict(BLANK_STRING);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nullAddressCity() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().setCity(null);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nullAddressCityId() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().getCity().setId(null);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nonExistentAddressCity() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getAddress().getCity().setId(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_noProducts() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.setOrderProducts(List.of());

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_invalidProductQuantity() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getOrderProducts().get(0).setQuantity(0);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_nonExistentProduct() throws Exception {
		OrderInsertDto dto = createInsertDto();
		dto.getOrderProducts().get(0).setProductId(NON_EXISTENT_ID);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_inactiveProduct() throws Exception {
		OrderInsertDto order = createInsertDto();
		Long restaurantId = order.getRestaurantId();
		Long productId = order.getOrderProducts().get(0).getProductId();
		ProductDto product = productCrudService.findDtoById(restaurantId, productId);

		product.setActive(Boolean.FALSE);
		productCrudService.update(restaurantId, productId, product);

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_WRITE"})
	void insert_fail_productFromAnotherRestaurant() throws Exception {
		OrderInsertDto dto = createInsertDto();
		Product product = productFactory.createAndPersist();
		dto.getOrderProducts().get(0).setProductId(product.getId());

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void insert_fail_unauthorized() throws Exception {
		OrderInsertDto dto = createInsertDto();

		getMockMvc().perform(post(API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	private void validateEntityPresenceInResponseList(MvcResult result, Order order) throws UnsupportedEncodingException {
		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.orders");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return ((Map<?, ?>) element).get("code").equals(order.getUuidCode());
			}
			return false;
		}));
	}

	private OrderInsertDto createInsertDto() {
		Order order = orderFactory.createEntity();
		OrderInsertDto insert = orderInsertMapper.toDto(order);
		Mockito.doReturn(order.getCustomer().getId()).when(appSecurity).getUserId();
		return insert;
	}
}
