package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Table(name = "rel_order_product")
@Entity
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_product")
	@SequenceGenerator(name = "seq_order_product", allocationSize = 1, sequenceName = "seq_order_product")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "quantity", nullable = false)
	private Integer quantity = 1;

	@Column(name = "unit_price", nullable = false)
	private Double unitPrice;

	@Column(name = "total_price", nullable = false)
	private Double totalPrice;

	@Column(name = "observation")
	private String observation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	private Product product;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderProduct that)) {
			return false;
		}
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
