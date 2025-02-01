package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_cuisine")
@Entity
public class Cuisine implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuisine")
	@SequenceGenerator(name = "seq_cuisine", allocationSize = 1, sequenceName = "seq_cuisine")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Cuisine cuisine)) {
			return false;
		}
		return getId().equals(cuisine.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}

