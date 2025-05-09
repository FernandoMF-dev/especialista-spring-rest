package br.com.colatina.fmf.algafood.service.domain.model;

import br.com.colatina.fmf.algafood.service.domain.events.OrderCanceledEvent;
import br.com.colatina.fmf.algafood.service.domain.events.OrderConfirmedEvent;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ConflictualResourceStatusException;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "tb_order")
@Entity
public class Order extends AbstractAggregateRoot<Order> {
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order")
	@SequenceGenerator(name = "seq_order", allocationSize = 1, sequenceName = "seq_order")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "uuid_code", nullable = false, unique = true)
	private String uuidCode;

	@Column(name = "total_value", nullable = false)
	private Double totalValue;

	@Column(name = "subtotal", nullable = false)
	private Double subtotal;

	@Column(name = "freight_fee", nullable = false)
	private Double freightFee = 0.0;

	@CreationTimestamp
	@Column(name = "registration_date", nullable = false)
	private OffsetDateTime registrationDate;

	@Column(name = "confirmation_date")
	private OffsetDateTime confirmationDate;

	@Column(name = "delivery_date")
	private OffsetDateTime deliveryDate;

	@Column(name = "cancellation_date")
	private OffsetDateTime cancellationDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 10)
	private OrderStatusEnum status = OrderStatusEnum.CREATED;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	private AppUser customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method_id", referencedColumnName = "id", nullable = false)
	private PaymentMethod paymentMethod;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	@Embedded
	private Address address;

	public void addSubTotal(Double value) {
		this.subtotal += value;
	}

	public void confirm() {
		setStatus(OrderStatusEnum.CONFIRMED);
		this.setConfirmationDate(OffsetDateTime.now());
		registerEvent(new OrderConfirmedEvent(this));
	}

	public void deliver() {
		setStatus(OrderStatusEnum.DELIVERED);
		this.setDeliveryDate(OffsetDateTime.now());
	}

	public void cancel() {
		setStatus(OrderStatusEnum.CANCELED);
		this.setCancellationDate(OffsetDateTime.now());
		registerEvent(new OrderCanceledEvent(this));
	}

	private void setStatus(OrderStatusEnum status) {
		if (!this.getStatus().canStatusChangeTo(status)) {
			throw new ConflictualResourceStatusException("order.status.conflictual_status", this.getId(), this.getStatus().getDescription(), status.getDescription());
		}
		this.status = status;
	}

	@PrePersist
	private void generateUuidCode() {
		this.uuidCode = UUID.randomUUID().toString();
	}
}
