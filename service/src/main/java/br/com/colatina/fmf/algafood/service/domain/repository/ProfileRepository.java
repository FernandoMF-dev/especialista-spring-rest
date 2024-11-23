package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfileRepository extends CustomJpaRepository<Profile, Long> {
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

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto" +
			"(pro.id, pro.name) " +
			" FROM AppUser u " +
			" INNER JOIN u.profiles pro " +
			" WHERE u.id = :userId " +
			" AND pro.excluded = FALSE ")
	Set<ProfileDto> findAllDtoByUser(Long userId);

	Optional<Profile> findByIdAndExcludedIsFalse(Long id);
}
