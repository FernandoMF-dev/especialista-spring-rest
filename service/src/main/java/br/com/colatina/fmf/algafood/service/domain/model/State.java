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
@Table(name = "tb_state")
@Entity
public class State implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_state")
	@SequenceGenerator(name = "seq_state", allocationSize = 1, sequenceName = "seq_state")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "acronym", length = 2, nullable = false)
	private String acronym;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof State state)) {
			return false;
		}
		return id.equals(state.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
