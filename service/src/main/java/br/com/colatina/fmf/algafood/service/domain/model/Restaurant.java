package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_restaurant")
@Entity
public class Restaurant implements Serializable {
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

	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PaymentMethod> paymentMethods;

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
