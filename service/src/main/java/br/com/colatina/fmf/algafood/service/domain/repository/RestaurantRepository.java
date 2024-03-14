package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.RestaurantRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant>, RestaurantRepositoryQueries {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto" +
			"(r.id, r.name, r.freightRate, r.active, k.name) " +
			" FROM Restaurant r " +
			" LEFT JOIN r.kitchen k " +
			" WHERE r.excluded = FALSE ")
	List<RestaurantListDto> findAllDto();

	Optional<Restaurant> findByIdAndExcludedIsFalse(Long id);
}
