package br.com.colatina.fmf.algafood.service.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_city")
@Entity
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
	@SequenceGenerator(name = "seq_city", allocationSize = 1, sequenceName = "seq_city")
	@Column(name = "id")
	private Long id;

	@Column(name = "acronym", length = 2)
	private String acronym;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id", referencedColumnName = "id")
	private State state;

	@Column(name = "excluded")
	private Boolean excluded = Boolean.FALSE;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof City)) {
			return false;
		}
		City city = (City) o;
		return id.equals(city.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
