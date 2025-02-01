package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_permission")
@Entity
public class Permission implements Serializable {
	@Id
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
		if (!(o instanceof Permission that)) {
			return false;
		}
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
