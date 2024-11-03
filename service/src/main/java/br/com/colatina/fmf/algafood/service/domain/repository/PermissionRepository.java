package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Permission;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto" +
			"(per.id, per.name) " +
			" FROM Profile pro " +
			" INNER JOIN pro.permissions per " +
			" WHERE pro.id = :profileId " +
			" AND per.excluded = FALSE ")
	Set<PermissionDto> findAllDtoByProfile(Long profileId);

	Optional<Permission> findByIdAndExcludedIsFalse(Long id);
}
