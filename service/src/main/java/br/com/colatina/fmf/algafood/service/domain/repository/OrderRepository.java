package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto" +
			"(o.uuidCode, o.totalValue, o.status, o.registrationDate, u.id, u.name, r.id, r.name) " +
			" FROM Order o " +
			" INNER JOIN o.user u " +
			" INNER JOIN o.restaurant r")
	List<OrderListDto> findAllListDto();

	@Query("SELECT o " +
			" FROM Order o " +
			" WHERE CAST(o.uuidCode as string) = :uuid ")
	Optional<Order> findByUuid(@Param("uuid") String uuid);
}
