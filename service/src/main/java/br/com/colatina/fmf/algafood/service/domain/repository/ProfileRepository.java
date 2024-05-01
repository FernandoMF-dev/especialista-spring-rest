package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto" +
			"(p.id, p.name) " +
			" FROM Profile p " +
			" WHERE p.excluded = FALSE ")
	List<ProfileDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto" +
			"(p.id, p.name) " +
			" FROM Profile p " +
			" WHERE p.excluded = FALSE " +
			" AND p.id = :id ")
	Optional<ProfileDto> findDtoById(Long id);

	Optional<Profile> findByIdAndExcludedIsFalse(Long id);
}
