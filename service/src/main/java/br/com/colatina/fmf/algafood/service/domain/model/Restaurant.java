package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_restaurant_responsible",
			joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private Set<AppUser> responsible = new HashSet<>();

	@Embedded
	private Address address;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Restaurant that)) {
			return false;
		}
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public boolean isOpenToOrder() {
		return this.getActive() && this.getOpen();
	}

	public boolean acceptsPaymentMethod(PaymentMethod paymentMethod) {
		return this.getPaymentMethods().contains(paymentMethod);
	}

	public void addResponsible(AppUser appUser) {
		this.getResponsible().add(appUser);
	}

	public void removeResponsible(AppUser appUser) {
		this.getResponsible().remove(appUser);
	}
}
