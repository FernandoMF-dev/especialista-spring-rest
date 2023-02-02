package br.com.colatina.fmf.algafood.service.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
	private String name;

	@Column(name = "excluded")
	private Boolean excluded = Boolean.FALSE;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Kitchen)) {
			return false;
		}
		Kitchen kitchen = (Kitchen) o;
		return getId().equals(kitchen.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}

