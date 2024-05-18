package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ConflictualResourceStatusException;
import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderFlowService {
	private final OrderCrudService orderCrudService;

	public void confirm(Long orderId) {
		Order order = orderCrudService.findEntityById(orderId);
		patchOrderStatus(order, OrderStatusEnum.CONFIRMED, OrderStatusEnum.CREATED);
		order.setConfirmationDate(OffsetDateTime.now());
	}

	public void deliver(Long orderId) {
		Order order = orderCrudService.findEntityById(orderId);
		patchOrderStatus(order, OrderStatusEnum.DELIVERED, OrderStatusEnum.CONFIRMED);
		order.setDeliveryDate(OffsetDateTime.now());
	}

	public void cancel(Long orderId) {
		Order order = orderCrudService.findEntityById(orderId);
		patchOrderStatus(order, OrderStatusEnum.CANCELED, OrderStatusEnum.CREATED, OrderStatusEnum.CONFIRMED);
		order.setCancellationDate(OffsetDateTime.now());
	}

	private void patchOrderStatus(Order order, OrderStatusEnum newStatus, OrderStatusEnum... allowedStatuses) {
		if (!Arrays.asList(allowedStatuses).contains(order.getStatus())) {
			throw new ConflictualResourceStatusException("order.status.conflictual_status", order.getId(), order.getStatus().getDescription(), newStatus.getDescription());
		}

		order.setStatus(newStatus);
	}
}
