package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderProductListDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends CustomJpaRepository<OrderProduct, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.OrderProductListDto" +
			"(op.id, op.quantity, op.observation, op.product.id, op.product.name, o.id) " +
			" FROM OrderProduct op " +
			" INNER JOIN op.order o " +
			" WHERE o.id IN (:orderIds) ")
	List<OrderProductListDto> findAllByOrder(List<Long> orderIds);
}
