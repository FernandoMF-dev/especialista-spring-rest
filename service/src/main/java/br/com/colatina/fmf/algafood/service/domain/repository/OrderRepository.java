package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto" +
			"(o.id, o.totalValue, o.status, o.registrationDate, u.id, u.name, r.id, r.name) " +
			" FROM Order o " +
			" INNER JOIN o.user u " +
			" INNER JOIN o.restaurant r")
	List<OrderListDto> findAllListDto();
}
