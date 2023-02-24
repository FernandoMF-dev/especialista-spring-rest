package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto" +
			"(k.id, k.name) " +
			" FROM Kitchen k " +
			" WHERE k.excluded = FALSE ")
	List<KitchenDto> findAllDto();

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto" +
			"(k.id, k.name) " +
			" FROM Kitchen k " +
			" WHERE k.id = :id " +
			" AND k.excluded = FALSE")
	Optional<KitchenDto> findDtoById(Long id);

	Optional<Kitchen> findByIdAndExcludedIsFalse(Long id);

	boolean isKitchenInUse(Long kitchenId);

}
