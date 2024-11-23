package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends CustomJpaRepository<AppUser, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto" +
			"(u.id, u.name, u.email) " +
			" FROM AppUser u " +
			" WHERE u.excluded = FALSE ")
	List<AppUserDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto" +
			"(u.id, u.name, u.email) " +
			" FROM AppUser u " +
			" WHERE u.id = :id " +
			" AND u.excluded = FALSE")
	Optional<AppUserDto> findDtoById(Long id);

	Optional<AppUser> findByIdAndExcludedIsFalse(Long id);

	Optional<AppUser> findByEmailAndExcludedIsFalse(String email);
}
