package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.RestaurantRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto" +
			"(r.id, r.name, r.freightRate, r.registrationDate, r.updateDate, r.active, k.id) " +
			" FROM Restaurant r " +
			" LEFT JOIN r.kitchen k " +
			" WHERE r.excluded = FALSE ")
	List<RestaurantDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto" +
			"(r.id, r.name, r.freightRate, r.registrationDate, r.updateDate, r.active, k.id) " +
			" FROM Restaurant r " +
			" LEFT JOIN r.kitchen k " +
			" WHERE r.id = :id " +
			" AND r.excluded = FALSE")
	Optional<RestaurantDto> findDtoById(Long id);

	Optional<Restaurant> findByIdAndExcludedIsFalse(Long id);

}
