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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table(name = "tb_profile")
@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profile")
	@SequenceGenerator(name = "seq_profile", allocationSize = 1, sequenceName = "seq_profile")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_profile_permission",
			joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
	private Set<Permission> permissions = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Profile)) {
			return false;
		}
		Profile that = (Profile) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void addPermission(Permission permission) {
		this.getPermissions().add(permission);
	}

	public void removePermission(Permission permission) {
		this.getPermissions().remove(permission);
	}
}
