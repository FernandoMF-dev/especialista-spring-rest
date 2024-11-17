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
			"(r.id, r.name, r.freightFee, r.active, r.open, k.id, k.name) " +
			" FROM Restaurant r " +
			" LEFT JOIN r.cuisine k " +
			" WHERE r.excluded = FALSE ")
	List<RestaurantListDto> findAllDto();

	// Query implementada no arquivo `orm.xml`
	boolean existsResponsible(Long restaurantId, Long responsibleId);

	Optional<Restaurant> findByIdAndExcludedIsFalse(Long id);
}
