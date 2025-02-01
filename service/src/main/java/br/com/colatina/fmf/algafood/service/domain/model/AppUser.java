package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_user")
@Entity
public class AppUser {
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@SequenceGenerator(name = "seq_user", allocationSize = 1, sequenceName = "seq_user")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@CreationTimestamp
	@Column(name = "registration_date", nullable = false)
	private OffsetDateTime registrationDate;

	@Column(name = "excluded", nullable = false)
	private Boolean excluded = Boolean.FALSE;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_user_profile",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
	private Set<Profile> profiles = new HashSet<>();

	public boolean isNew() {
		return Objects.isNull(this.getId());
	}

	public void addProfile(Profile profile) {
		this.getProfiles().add(profile);
	}

	public void removeProfile(Profile profile) {
		this.getProfiles().remove(profile);
	}
}
