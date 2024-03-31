package br.com.colatina.fmf.algafood.service.domain.model;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_order")
@Entity
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order")
	@SequenceGenerator(name = "seq_order", allocationSize = 1, sequenceName = "seq_order")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "total_value", nullable = false)
	private Double totalValue;

	@Column(name = "subtotal", nullable = false)
	private Double subtotal;

	@Column(name = "freight_fee", nullable = false)
	private Double freightFee = 0.0;

	@CreationTimestamp
	@Column(name = "registration_date", nullable = false)
	private LocalDateTime registrationDate;

	@Column(name = "confirmation_date")
	private LocalDateTime confirmationDate;

	@Column(name = "delivery_date")
	private LocalDateTime deliveryDate;

	@Column(name = "cancellation_date")
	private LocalDateTime cancellationDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 10)
	private OrderStatusEnum status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method_id", referencedColumnName = "id", nullable = false)
	private PaymentMethod paymentMethod;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	@Embedded
	private Address address;

	public Order(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Order)) {
			return false;
		}
		Order that = (Order) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
