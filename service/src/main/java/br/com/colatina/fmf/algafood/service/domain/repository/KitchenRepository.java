package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

	Optional<Kitchen> findByIdAndExcludedIsFalse(Long id);

}
