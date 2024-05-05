package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotAvailableException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import br.com.colatina.fmf.algafood.service.domain.repository.OrderRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderInsertMapper;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCrudService {
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final OrderInsertMapper orderInsertMapper;

	private final UserCrudService userCrudService;
	private final RestaurantCrudService restaurantCrudService;
	private final PaymentMethodCrudService paymentMethodCrudService;
	private final CityCrudService cityCrudService;
	private final OrderProductCrudService orderProductCrudService;

	public OrderDto insert(OrderInsertDto insertDto) {
		Order entity = orderInsertMapper.toEntity(insertDto);
		List<OrderProduct> orderProducts = entity.getOrderProducts();

		validateInsertEntities(insertDto, entity);
		validateInsertRestaurant(entity);
		validateInsertPaymentMethod(entity);
		validateInsertProductsAndPrice(orderProducts, entity);

		entity.setStatus(OrderStatusEnum.CREATED);
		entity = orderRepository.save(entity);

		OrderDto dto = orderMapper.toDto(entity);
		List<OrderProductDto> productsDto = orderProductCrudService.insertItensOnOrder(orderProducts, entity.getId());
		dto.setOrderProducts(productsDto);

		return dto;
	}

	private void validateInsertEntities(OrderInsertDto insertDto, Order entity) {
		try {
			entity.setUser(userCrudService.findEntityById(insertDto.getUserId()));
			entity.setRestaurant(restaurantCrudService.findEntityById(insertDto.getRestaurantId()));
			entity.setPaymentMethod(paymentMethodCrudService.findEntityById(insertDto.getPaymentMethodId()));
			entity.getAddress().setCity(cityCrudService.findEntityById(insertDto.getAddress().getCity().getId()));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e, HttpStatus.BAD_REQUEST);
		}
	}

	private void validateInsertRestaurant(Order entity) {
		if (!entity.getRestaurant().isOpenToOrder()) {
			throw new ResourceNotAvailableException("order_insert.restaurant.not_open");
		}
	}

	private void validateInsertPaymentMethod(Order entity) {
		if (!entity.getRestaurant().acceptsPaymentMethod(entity.getPaymentMethod())) {
			throw new ResourceNotAvailableException("payment_method.not_available.restaurant");
		}
	}

	private void validateInsertProductsAndPrice(List<OrderProduct> orderProducts, Order entity) {
		entity.setFreightFee(entity.getRestaurant().getFreightFee());
		entity.setSubtotal(0.0);

		orderProducts.forEach(orderProduct -> {
			Product product = entity.getRestaurant().getProducts().stream()
					.filter(element -> doesRestaurantOffersProduct(orderProduct.getProduct(), element))
					.findFirst()
					.orElseThrow(() -> new ResourceNotAvailableException("product.not_available.restaurant", orderProduct.getProduct().getId()));

			mapOrderProductInsertPrice(orderProduct, product);
			entity.addSubTotal(orderProduct.getTotalPrice());
		});

		entity.setTotalValue(entity.getSubtotal() + entity.getFreightFee());
	}

	private void mapOrderProductInsertPrice(OrderProduct orderProduct, Product product) {
		orderProduct.setProduct(product);
		orderProduct.setUnitPrice(product.getPrice());
		orderProduct.setTotalPrice(orderProduct.getUnitPrice() * orderProduct.getQuantity());
	}

	private boolean doesRestaurantOffersProduct(Product orderProduct, Product targetProduct) {
		return Objects.equals(targetProduct, orderProduct) && !targetProduct.getExcluded() && targetProduct.getActive();
	}

}
