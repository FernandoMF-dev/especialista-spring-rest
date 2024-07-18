package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends CustomJpaRepository<PaymentMethod, Long> {

	@Query("SELECT MAX(pm.updateDate) " +
			" FROM PaymentMethod pm")
	OffsetDateTime findLastUpdate();

	@Query("SELECT MAX(pm.updateDate) " +
			" FROM PaymentMethod pm " +
			" WHERE pm.id = :id " +
			" AND pm.excluded = FALSE ")
	OffsetDateTime findLastUpdateById(Long id);

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto" +
			"(pm.id, pm.description) " +
			" FROM PaymentMethod pm " +
			" WHERE pm.excluded = FALSE ")
	List<PaymentMethodDto> findAllDto();

	@Query("SELECT pm " +
			" FROM PaymentMethod pm " +
			" WHERE pm.excluded = FALSE " +
			" AND pm.id IN (:ids)")
	List<PaymentMethod> findAllById(List<Long> ids);

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto" +
			"(pm.id, pm.description) " +
			" FROM PaymentMethod pm " +
			" WHERE pm.excluded = FALSE " +
			" AND pm.id = :id ")
	Optional<PaymentMethodDto> findDtoById(Long id);

	Optional<PaymentMethod> findByIdAndExcludedIsFalse(Long id);
}
