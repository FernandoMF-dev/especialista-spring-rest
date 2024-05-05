package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CustomJpaRepository<OrderProduct, Long> {
}
