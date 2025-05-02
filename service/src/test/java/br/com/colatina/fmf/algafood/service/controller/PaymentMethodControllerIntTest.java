package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.api.utils.ResourceUriUtils;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod_;
import br.com.colatina.fmf.algafood.service.domain.service.PaymentMethodCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.PaymentMethodMapper;
import br.com.colatina.fmf.algafood.service.factory.PaymentMethodFactory;
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

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentMethodControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_PAYMENT_METHOD = "/api/v1/payment-methods";

	@Autowired
	private PaymentMethodFactory paymentMethodFactory;
	@Autowired
	private PaymentMethodMapper paymentMethodMapper;
	@Autowired
	private PaymentMethodCrudService paymentMethodCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		MvcResult result = getMockMvc().perform(get(API_PAYMENT_METHOD))
				.andExpect(status().isOk())
				.andExpect(header().exists(HttpHeaders.ETAG))
				.andExpect(jsonPath("$._embedded.payment_methods").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray paymentMethods = JsonPath.parse(response).read("$._embedded.payment_methods");

		validateEntityPresenceInResponseList(paymentMethods, entity.getId());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findAll_success_etag() throws Exception {
		paymentMethodFactory.createAndPersist();
		OffsetDateTime lastUpdate = paymentMethodCrudService.findLastUpdate();
		String eTag = '"' + ResourceUriUtils.getDeepETag(lastUpdate) + '"';

		getMockMvc().perform(get(API_PAYMENT_METHOD)
						.header(HttpHeaders.IF_NONE_MATCH, eTag))
				.andExpect(status().isNotModified())
				.andExpect(header().string(HttpHeaders.ETAG, eTag))
				.andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	@WithMockUser(username = "tester")
	void findAll_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_PAYMENT_METHOD))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		getMockMvc().perform(get(API_PAYMENT_METHOD.concat("/{id}"), entity.getId()))
				.andExpect(status().isOk())
				.andExpect(header().exists(HttpHeaders.ETAG))
				.andExpect(jsonPath("$." + PaymentMethod_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + PaymentMethod_.DESCRIPTION, Matchers.equalTo(entity.getDescription())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_success_etag() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();
		OffsetDateTime lastUpdate = paymentMethodCrudService.findLastUpdateById(entity.getId());
		String eTag = '"' + ResourceUriUtils.getDeepETag(lastUpdate) + '"';

		getMockMvc().perform(get(API_PAYMENT_METHOD.concat("/{id}"), entity.getId())
						.header(HttpHeaders.IF_NONE_MATCH, eTag))
				.andExpect(status().isNotModified())
				.andExpect(header().string(HttpHeaders.ETAG, eTag));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(get(API_PAYMENT_METHOD.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findById_fail_deletedEntity() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		paymentMethodCrudService.delete(entity.getId());

		getMockMvc().perform(get(API_PAYMENT_METHOD.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void findById_fail_unauthorized() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		getMockMvc().perform(get(API_PAYMENT_METHOD.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_PAYMENT_METHOD", "SCOPE_WRITE"})
	void insert_success() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createEntity();
		PaymentMethodDto dto = paymentMethodMapper.toDto(entity);

		getMockMvc().perform(post(API_PAYMENT_METHOD)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(jsonPath("$." + PaymentMethod_.ID, Matchers.notNullValue()))
				.andExpect(jsonPath("$." + PaymentMethod_.DESCRIPTION, Matchers.equalTo(entity.getDescription())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"CREATE_PAYMENT_METHOD", "SCOPE_WRITE"})
	void insert_fail_blankDescription() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createEntity();
		PaymentMethodDto dto = paymentMethodMapper.toDto(entity);
		dto.setDescription(BLANK_STRING);

		getMockMvc().perform(post(API_PAYMENT_METHOD)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void insert_fail_unauthorized() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createEntity();
		PaymentMethodDto dto = paymentMethodMapper.toDto(entity);

		getMockMvc().perform(post(API_PAYMENT_METHOD)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_PAYMENT_METHOD", "SCOPE_WRITE"})
	void update_success() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();
		PaymentMethodDto dto = paymentMethodMapper.toDto(entity);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_PAYMENT_METHOD.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + PaymentMethod_.ID, Matchers.equalTo(entity.getId().intValue())))
				.andExpect(jsonPath("$." + PaymentMethod_.DESCRIPTION, Matchers.equalTo(dto.getDescription())));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_PAYMENT_METHOD", "SCOPE_WRITE"})
	void update_fail_blankDescription() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();
		PaymentMethodDto dto = paymentMethodMapper.toDto(entity);
		dto.setDescription(BLANK_STRING);

		getMockMvc().perform(put(API_PAYMENT_METHOD.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "tester")
	void update_fail_unauthorized() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();
		PaymentMethodDto dto = paymentMethodMapper.toDto(entity);
		dto.setDescription(dto.getDescription() + " updated");

		getMockMvc().perform(put(API_PAYMENT_METHOD.concat("/{id}"), entity.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(dto)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_PAYMENT_METHOD", "SCOPE_DELETE"})
	void delete_success() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		getMockMvc().perform(delete(API_PAYMENT_METHOD.concat("/{id}"), entity.getId()))
				.andExpect(status().isNoContent());

		PaymentMethod deleted = paymentMethodFactory.getById(entity.getId());
		Assertions.assertTrue(deleted.getExcluded());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_PAYMENT_METHOD", "SCOPE_DELETE"})
	void delete_fail_nonExistentEntity() throws Exception {
		getMockMvc().perform(delete(API_PAYMENT_METHOD.concat("/{id}"), NON_EXISTENT_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"DELETE_PAYMENT_METHOD", "SCOPE_DELETE"})
	void delete_fail_deletedEntity() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		paymentMethodCrudService.delete(entity.getId());

		getMockMvc().perform(delete(API_PAYMENT_METHOD.concat("/{id}"), entity.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void delete_fail_unauthorized() throws Exception {
		PaymentMethod entity = paymentMethodFactory.createAndPersist();

		getMockMvc().perform(delete(API_PAYMENT_METHOD.concat("/{id}"), entity.getId()))
				.andExpect(status().isForbidden());
	}
}
