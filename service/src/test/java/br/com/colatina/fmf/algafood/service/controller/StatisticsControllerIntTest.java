package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.service.OrderFlowService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.factory.OrderFactory;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticsControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_STATISTICS = "/api/v1/statistics";

	@Autowired
	private OrderFactory orderFactory;
	@Autowired
	private OrderFlowService orderFlowService;

	@Test
	@WithMockUser(username = "tester")
	void statistics_success() throws Exception {
		getMockMvc().perform(get(API_STATISTICS))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links").exists());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerDay_success_noFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		MvcResult result = getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-day")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.sales_per_period").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.sales_per_period");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return assertYear((Map<?, ?>) element, order) && assertMonth((Map<?, ?>) element, order) && asserDay((Map<?, ?>) element, order);
			}
			return false;
		}));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerDay_success_withFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		SalesPerPeriodFilter filter = buildFilter(order);

		MvcResult result = getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-day"))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.sales_per_period").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.sales_per_period");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return assertYear((Map<?, ?>) element, order) && assertMonth((Map<?, ?>) element, order) && asserDay((Map<?, ?>) element, order);
			}
			return false;
		}));
	}

	@Test
	@WithMockUser(username = "tester")
	void findSalesPerDay_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-day")))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerDayPdf_success_noFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-day"))
						.accept(MediaType.APPLICATION_PDF_VALUE))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerDayPdf_success_withFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		SalesPerPeriodFilter filter = buildFilter(order);

		getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-day"))
						.accept(MediaType.APPLICATION_PDF_VALUE)
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE));
	}

	@Test
	@WithMockUser(username = "tester")
	void findSalesPerDayPdf_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-day"))
						.accept(MediaType.APPLICATION_PDF_VALUE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerMonth_success_noFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		MvcResult result = getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-month")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.sales_per_period").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.sales_per_period");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return assertYear((Map<?, ?>) element, order) && assertMonth((Map<?, ?>) element, order);
			}
			return false;
		}));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerMonth_success_withFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		SalesPerPeriodFilter filter = buildFilter(order);

		MvcResult result = getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-month"))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.sales_per_period").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.sales_per_period");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return assertYear((Map<?, ?>) element, order) && assertMonth((Map<?, ?>) element, order);
			}
			return false;
		}));
	}

	@Test
	@WithMockUser(username = "tester")
	void findSalesPerMonth_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-month")))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerYear_success_noFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		MvcResult result = getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-year")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.sales_per_period").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.sales_per_period");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return assertYear((Map<?, ?>) element, order);
			}
			return false;
		}));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"EMIT_SALES_REPORT", "SCOPE_READ"})
	void findSalesPerYear_success_withFilter() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		SalesPerPeriodFilter filter = buildFilter(order);

		MvcResult result = getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-year"))
						.queryParams(convertObjectToParams(filter)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.sales_per_period").isArray())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		JSONArray list = JsonPath.parse(response).read("$._embedded.sales_per_period");

		Assertions.assertTrue(list.stream().anyMatch(element -> {
			if (element instanceof Map) {
				return assertYear((Map<?, ?>) element, order);
			}
			return false;
		}));
	}

	@Test
	@WithMockUser(username = "tester")
	void findSalesPerYear_fail_unauthorized() throws Exception {
		getMockMvc().perform(get(API_STATISTICS.concat("/sales-per-year")))
				.andExpect(status().isForbidden());
	}

	private SalesPerPeriodFilter buildFilter(Order order) {
		SalesPerPeriodFilter filter = new SalesPerPeriodFilter();
		filter.setRestaurantId(order.getRestaurant().getId());
		filter.setStartDate(order.getRegistrationDate().minusDays(5));
		filter.setEndDate(order.getRegistrationDate().plusDays(5));
		filter.setTimeOffset("+00:00");
		return filter;
	}

	private boolean assertYear(Map<?, ?> element, Order order) {
		return element.get("year").equals(order.getRegistrationDate().getYear());
	}

	private boolean assertMonth(Map<?, ?> element, Order order) {
		return element.get("month").equals(order.getRegistrationDate().getMonthValue());
	}

	private boolean asserDay(Map<?, ?> element, Order order) {
		return element.get("day").equals(order.getRegistrationDate().getDayOfMonth());
	}
}
