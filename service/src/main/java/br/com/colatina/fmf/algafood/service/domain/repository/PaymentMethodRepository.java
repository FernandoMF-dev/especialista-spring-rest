package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
	@Query("SELECT pm " +
			" FROM PaymentMethod pm " +
			" WHERE pm.excluded = FALSE " +
			" AND pm.id IN (:ids)")
	List<PaymentMethod> findAllById(List<Long> ids);

	Optional<PaymentMethod> findByIdAndExcludedIsFalse(Long id);
}
