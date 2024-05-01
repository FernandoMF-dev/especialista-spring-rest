package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.City;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto" +
			"(c.id, c.acronym, c.name, s.id, s.acronym, s.name) " +
			" FROM City c " +
			" INNER JOIN c.state s " +
			" WHERE c.excluded = FALSE ")
	List<CityDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto" +
			"(c.id, c.acronym, c.name, s.id, s.acronym, s.name) " +
			" FROM City c " +
			" INNER JOIN c.state s " +
			" WHERE c.id = :id " +
			" AND c.excluded = FALSE")
	Optional<CityDto> findDtoById(Long id);

	Optional<City> findByIdAndExcludedIsFalse(Long id);

}
