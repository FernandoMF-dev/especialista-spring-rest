package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuisineRepository extends CustomJpaRepository<Cuisine, Long> {

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto" +
			"(k.id, k.name) " +
			" FROM Cuisine k " +
			" WHERE k.excluded = FALSE ")
	List<CuisineDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto" +
			"(k.id, k.name) " +
			" FROM Cuisine k " +
			" WHERE k.id = :id " +
			" AND k.excluded = FALSE")
	Optional<CuisineDto> findDtoById(Long id);

	Optional<Cuisine> findByIdAndExcludedIsFalse(Long id);

	boolean isCuisineInUse(Long cuisineId);

}
