package br.com.colatina.fmf.algafood.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApiUser, Long> {
	Optional<ApiUser> findByIdAndExcludedIsFalse(Long id);

	Optional<ApiUser> findByEmailAndExcludedIsFalse(String email);
}
