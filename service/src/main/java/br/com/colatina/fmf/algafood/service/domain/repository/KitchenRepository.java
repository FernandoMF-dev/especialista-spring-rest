package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

	Optional<Kitchen> findByIdAndExcludedIsFalse(Long id);

	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
			" FROM Restaurant r " +
			" WHERE r.kitchen.id = :kitchenId " +
			" AND r.excluded = FALSE " +
			" AND r.kitchen.excluded = FALSE ")
	boolean isKitchenInUse(Long kitchenId);

}
