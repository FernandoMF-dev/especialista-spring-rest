package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import br.com.colatina.fmf.algafood.service.domain.service.OrderCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.OrderFlowService;
import br.com.colatina.fmf.algafood.service.factory.OrderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderFlowControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_ORDER_FLOW = "/api/v1/orders/{orderUuid}";

	@Autowired
	private OrderFactory orderFactory;
	@Autowired
	private OrderCrudService orderCrudService;
	@Autowired
	private OrderFlowService orderFlowService;

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void confirm_success() throws Exception {
		Order order = orderFactory.createAndPersist();

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/confirm"), order.getUuidCode()))
				.andExpect(status().isNoContent());

		assertNewStatus(order.getUuidCode(), OrderStatusEnum.CONFIRMED);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void confirm_fail_conflictualStatus() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/confirm"), order.getUuidCode()))
				.andExpect(status().isConflict());
	}

	@Test
	@WithMockUser(username = "tester")
	void confirm_fail_unauthorized() throws Exception {
		Order order = orderFactory.createAndPersist();

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/confirm"), order.getUuidCode()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void deliver_success() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/deliver"), order.getUuidCode()))
				.andExpect(status().isNoContent());

		assertNewStatus(order.getUuidCode(), OrderStatusEnum.DELIVERED);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void deliver_fail_conflictualStatus() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.cancel(order.getUuidCode());

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/deliver"), order.getUuidCode()))
				.andExpect(status().isConflict());
	}

	@Test
	@WithMockUser(username = "tester")
	void deliver_fail_unauthorized() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/deliver"), order.getUuidCode()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void cancel_success_fromCreated() throws Exception {
		Order order = orderFactory.createAndPersist();

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/cancel"), order.getUuidCode()))
				.andExpect(status().isNoContent());

		assertNewStatus(order.getUuidCode(), OrderStatusEnum.CANCELED);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void cancel_success_fromConfirmed() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/cancel"), order.getUuidCode()))
				.andExpect(status().isNoContent());

		assertNewStatus(order.getUuidCode(), OrderStatusEnum.CANCELED);
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"MANAGE_ORDER", "SCOPE_WRITE"})
	void cancel_fail_conflictualStatus() throws Exception {
		Order order = orderFactory.createAndPersist();
		orderFlowService.confirm(order.getUuidCode());
		orderFlowService.deliver(order.getUuidCode());

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/cancel"), order.getUuidCode()))
				.andExpect(status().isConflict());
	}

	@Test
	@WithMockUser(username = "tester")
	void cancel_fail_unauthorized() throws Exception {
		Order order = orderFactory.createAndPersist();

		getMockMvc().perform(put(API_ORDER_FLOW.concat("/cancel"), order.getUuidCode()))
				.andExpect(status().isForbidden());
	}

	private void assertNewStatus(String uuid, OrderStatusEnum status) {
		Order modified = orderCrudService.findEntityByUuid(uuid);
		Assertions.assertEquals(status, modified.getStatus());
	}
}
