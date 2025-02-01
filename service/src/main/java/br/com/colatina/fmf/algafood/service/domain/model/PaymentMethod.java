package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_payment_method")
@Entity
@NoArgsConstructor
public class PaymentMethod implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_payment_method")
	@SequenceGenerator(name = "seq_payment_method", allocationSize = 1, sequenceName = "seq_payment_method")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "description", nullable = false)
	private String description;

	@UpdateTimestamp
	@Column(name = "update_date", nullable = false)
	private OffsetDateTime updateDate;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	public PaymentMethod(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PaymentMethod that)) {
			return false;
		}
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
