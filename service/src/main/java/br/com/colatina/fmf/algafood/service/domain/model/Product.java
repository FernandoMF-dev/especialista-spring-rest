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
@Table(name = "tb_product")
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product")
	@SequenceGenerator(name = "seq_product", allocationSize = 1, sequenceName = "seq_product")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "active", nullable = false)
	private Boolean active = Boolean.TRUE;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
	private Restaurant restaurant;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Product that)) {
			return false;
		}
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
