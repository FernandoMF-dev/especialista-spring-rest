package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.OrderRepository;
import br.com.colatina.fmf.algafood.service.domain.service.OrderCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderInsertMapper;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFactory extends BaseEntityFactory<Order> {
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderInsertMapper orderInsertMapper;
	@Autowired
	OrderCrudService orderCrudService;
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	AppUserFactory appUserFactory;
	@Autowired
	ProductFactory productFactory;
	@Autowired
	AddressFactory addressFactory;
	@Autowired
	RestaurantCrudService restaurantCrudService;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Order createEntity() {
		Product product1 = productFactory.createAndPersist();
		Restaurant restaurant = getRestaurant(product1.getRestaurant().getId());
		PaymentMethod paymentMethod = restaurant.getPaymentMethods().stream().toList().get(0);
		Product product2 = productFactory.createAndPersist(entity -> entity.setRestaurant(restaurant));

		restaurantCrudService.toggleOpen(restaurant.getId(), Boolean.TRUE);

		Order order = new Order();
		order.setCustomer(appUserFactory.createAndPersist());
		order.setRestaurant(restaurant);
		order.setPaymentMethod(paymentMethod);
		order.setOrderProducts(List.of(createOrderProduct(product1, 1), createOrderProduct(product2, 2)));
		order.setAddress(addressFactory.createEntity());
		return order;
	}

	@Override
	protected Order persist(Order entity) {
		OrderInsertDto insert = orderInsertMapper.toDto(entity);
		OrderDto dto = orderCrudService.insert(insert);
		return orderMapper.toEntity(dto);
	}

	@Override
	public Order getById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	public OrderProduct createOrderProduct(Product product, Integer quantity) {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setProduct(product);
		orderProduct.setQuantity(quantity);
		orderProduct.setObservation(String.format("Observation %d", System.currentTimeMillis()));
		return orderProduct;
	}

	private Restaurant getRestaurant(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Restaurant restaurant = session.find(Restaurant.class, id);
		Assertions.assertNotEquals(0, restaurant.getPaymentMethods().size());

		session.getTransaction().commit();
		session.close();

		return restaurant;
	}
}
