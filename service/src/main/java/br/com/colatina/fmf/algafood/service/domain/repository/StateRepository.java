package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.State;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends CustomJpaRepository<State, Long> {

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto" +
			"(s.id, s.acronym, s.name) " +
			" FROM State s " +
			" WHERE s.excluded = FALSE ")
	List<StateDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto" +
			"(s.id, s.acronym, s.name) " +
			" FROM State s " +
			" WHERE s.id = :id " +
			" AND s.excluded = FALSE")
	Optional<StateDto> findDtoById(Long id);

	Optional<State> findByIdAndExcludedIsFalse(Long id);

}
