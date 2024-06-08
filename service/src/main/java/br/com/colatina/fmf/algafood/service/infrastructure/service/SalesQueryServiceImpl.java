package br.com.colatina.fmf.algafood.service.infrastructure.service;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.Order_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SalesQueryServiceImpl implements SalesQueryService {
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";

	private static final String FN_DATE_TRUNC = "date_trunc";
	private static final String FN_AT_TIME_ZONE = "at_time_zone";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SalesPerPeriod> findSalesPerDay(SalesPerPeriodFilter filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SalesPerPeriod> query = builder.createQuery(SalesPerPeriod.class);
		Root<Order> root = query.from(Order.class);
		List<Predicate> predicates = getSalerPerPeriodPredicates(filter, root, builder);

		var fnDateRegistrationDate = builder.function(FN_AT_TIME_ZONE, LocalDate.class, root.get(Order_.REGISTRATION_DATE), builder.literal(filter.getTimeOffset()));
		fnDateRegistrationDate = builder.function(FN_DATE_TRUNC, LocalDate.class, builder.literal(DAY), fnDateRegistrationDate);
		var fnYearRegistrationDate = builder.function(YEAR, Integer.class, fnDateRegistrationDate);
		var fnMonthRegistrationDate = builder.function(MONTH, Integer.class, fnDateRegistrationDate);
		var fnDayRegistrationDate = builder.function(DAY, Integer.class, fnDateRegistrationDate);

		var selection = builder.construct(SalesPerPeriod.class,
				builder.count(root.get(Order_.ID)),
				builder.sum(root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate,
				fnMonthRegistrationDate,
				fnDayRegistrationDate
		);

		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(fnDateRegistrationDate);

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<SalesPerPeriod> findSalesPerMonth(SalesPerPeriodFilter filter) {
		// TODO implement this method
		return List.of();
	}

	@Override
	public List<SalesPerPeriod> findSalesPerYear(SalesPerPeriodFilter filter) {
		// TODO implement this method
		return List.of();
	}

	private static List<Predicate> getSalerPerPeriodPredicates(SalesPerPeriodFilter filter, Root<Order> root, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(root.get(Order_.STATUS).in(OrderStatusEnum.CONFIRMED, OrderStatusEnum.DELIVERED));

		if (Objects.nonNull(filter.getRestaurantId())) {
			predicates.add(builder.equal(root.get(Order_.RESTAURANT).get(Restaurant_.ID), filter.getRestaurantId()));
		}

		if (Objects.nonNull(filter.getStartDate())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Order_.REGISTRATION_DATE), filter.getStartDate()));
		}

		if (Objects.nonNull(filter.getEndDate())) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Order_.REGISTRATION_DATE), filter.getEndDate()));
		}

		return predicates;
	}
}
