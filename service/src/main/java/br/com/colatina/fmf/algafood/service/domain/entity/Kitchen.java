package br.com.colatina.fmf.algafood.service.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_kitchen")
@Entity
public class Kitchen {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_kitchen")
	@SequenceGenerator(name = "seq_kitchen", allocationSize = 1, sequenceName = "seq_kitchen")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String nome;

	@Column(name = "excluded")
	private Boolean excluido = Boolean.FALSE;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Kitchen)) return false;
		Kitchen kitchen = (Kitchen) o;
		return getId().equals(kitchen.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}

