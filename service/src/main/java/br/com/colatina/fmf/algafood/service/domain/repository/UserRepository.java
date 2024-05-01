package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto" +
			"(u.id, u.name, u.email) " +
			" FROM User u " +
			" WHERE u.excluded = FALSE ")
	List<UserDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto" +
			"(u.id, u.name, u.email) " +
			" FROM User u " +
			" WHERE u.id = :id " +
			" AND u.excluded = FALSE")
	Optional<UserDto> findDtoById(Long id);

	Optional<User> findByIdAndExcludedIsFalse(Long id);
}
