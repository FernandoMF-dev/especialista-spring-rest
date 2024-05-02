package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table(name = "tb_restaurant")
@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_restaurant")
	@SequenceGenerator(name = "seq_restaurant", allocationSize = 1, sequenceName = "seq_restaurant")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "freight_fee", nullable = false)
	private Double freightFee;

	@CreationTimestamp
	@Column(name = "registration_date", nullable = false)
	private OffsetDateTime registrationDate;

	@UpdateTimestamp
	@Column(name = "update_date", nullable = false)
	private OffsetDateTime updateDate;

	@Column(name = "active", nullable = false)
	private Boolean active = Boolean.TRUE;

	@Column(name = "open", nullable = false)
	private Boolean open = Boolean.FALSE;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuisine_id", referencedColumnName = "id", nullable = false)
	private Cuisine cuisine;

	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_restaurant_payment_method",
			joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "payment_method_id", referencedColumnName = "id"))
	private Set<PaymentMethod> paymentMethods = new HashSet<>();

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
