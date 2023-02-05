package br.com.colatina.fmf.algafood.service.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tb_kitchen")
@Entity
@ToString
@JsonRootName("kitchen")
public class Kitchen implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_kitchen")
	@SequenceGenerator(name = "seq_kitchen", allocationSize = 1, sequenceName = "seq_kitchen")
	@Column(name = "id")
	private Long id;

	@NonNull
	@Column(name = "name")
	private String name;

	@JsonIgnore
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

