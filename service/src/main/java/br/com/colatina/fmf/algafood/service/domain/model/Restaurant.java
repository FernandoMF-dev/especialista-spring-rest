package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_restaurant")
@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_restaurant")
	@SequenceGenerator(name = "seq_restaurant", allocationSize = 1, sequenceName = "seq_restaurant")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "freight_rate")
	private Double freightRate;

	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "active")
	private Boolean active = Boolean.TRUE;

	@Column(name = "excluded")
	private Boolean excluded = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kitchen_id", referencedColumnName = "id")
	private Kitchen kitchen;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_restaurant_payment_method",
			joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "payment_method_id", referencedColumnName = "id"))
	private List<PaymentMethod> paymentMethods = new ArrayList<>();

	@Embedded
	private Address address;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Restaurant)) {
			return false;
		}
		Restaurant that = (Restaurant) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
