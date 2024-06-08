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
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
		SalerPerPeriodCriteria criteria = new SalerPerPeriodCriteria(filter);

		var fnDateRegistrationDate = _fnDateRegistrationDate(criteria.builder, criteria.root, filter, DAY);
		var fnYearRegistrationDate = _fnYearRegistrationDate(criteria.builder, fnDateRegistrationDate);
		var fnMonthRegistrationDate = _fnMonthRegistrationDate(criteria.builder, fnDateRegistrationDate);
		var fnDayRegistrationDate = _fnDayRegistrationDate(criteria.builder, fnDateRegistrationDate);

		var selection = criteria.builder.construct(SalesPerPeriod.class,
				criteria.builder.count(criteria.root.get(Order_.ID)),
				criteria.builder.sum(criteria.root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate,
				fnMonthRegistrationDate,
				fnDayRegistrationDate
		);

		criteria.prepareQuery(selection, fnDateRegistrationDate);
		return entityManager.createQuery(criteria.query).getResultList();
	}

	@Override
	public List<SalesPerPeriod> findSalesPerMonth(SalesPerPeriodFilter filter) {
		SalerPerPeriodCriteria criteria = new SalerPerPeriodCriteria(filter);

		var fnDateRegistrationDate = _fnDateRegistrationDate(criteria.builder, criteria.root, filter, MONTH);
		var fnYearRegistrationDate = _fnYearRegistrationDate(criteria.builder, fnDateRegistrationDate);
		var fnMonthRegistrationDate = _fnMonthRegistrationDate(criteria.builder, fnDateRegistrationDate);

		var selection = criteria.builder.construct(SalesPerPeriod.class,
				criteria.builder.count(criteria.root.get(Order_.ID)),
				criteria.builder.sum(criteria.root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate,
				fnMonthRegistrationDate
		);

		criteria.prepareQuery(selection, fnDateRegistrationDate);
		return entityManager.createQuery(criteria.query).getResultList();
	}

	@Override
	public List<SalesPerPeriod> findSalesPerYear(SalesPerPeriodFilter filter) {
		SalerPerPeriodCriteria criteria = new SalerPerPeriodCriteria(filter);

		var fnDateRegistrationDate = _fnDateRegistrationDate(criteria.builder, criteria.root, filter, YEAR);
		var fnYearRegistrationDate = _fnYearRegistrationDate(criteria.builder, fnDateRegistrationDate);

		var selection = criteria.builder.construct(SalesPerPeriod.class,
				criteria.builder.count(criteria.root.get(Order_.ID)),
				criteria.builder.sum(criteria.root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate
		);

		criteria.prepareQuery(selection, fnDateRegistrationDate);
		return entityManager.createQuery(criteria.query).getResultList();
	}

	// <editor-fold defaultstate="collapsed" desc="Criteria query expressions">
	private Expression<LocalDate> _fnDateRegistrationDate(CriteriaBuilder builder, Root<Order> root, SalesPerPeriodFilter filter, String dateTruncParam) {
		var fnDateRegistrationDate = builder.function(FN_AT_TIME_ZONE, LocalDate.class, root.get(Order_.REGISTRATION_DATE), builder.literal(filter.getTimeOffset()));
		return builder.function(FN_DATE_TRUNC, LocalDate.class, builder.literal(dateTruncParam), fnDateRegistrationDate);
	}

	private Expression<Integer> _fnYearRegistrationDate(CriteriaBuilder builder, Expression<LocalDate> fnDateRegistrationDate) {
		return builder.function(YEAR, Integer.class, fnDateRegistrationDate);
	}

	private Expression<Integer> _fnMonthRegistrationDate(CriteriaBuilder builder, Expression<LocalDate> fnDateRegistrationDate) {
		return builder.function(MONTH, Integer.class, fnDateRegistrationDate);
	}

	private Expression<Integer> _fnDayRegistrationDate(CriteriaBuilder builder, Expression<LocalDate> fnDateRegistrationDate) {
		return builder.function(DAY, Integer.class, fnDateRegistrationDate);
	}
	// </editor-fold>

	private class SalerPerPeriodCriteria {
		public final CriteriaBuilder builder;
		public final CriteriaQuery<SalesPerPeriod> query;
		public final Root<Order> root;
		public final List<Predicate> predicates;

		public SalerPerPeriodCriteria(SalesPerPeriodFilter filter) {
			this.builder = entityManager.getCriteriaBuilder();
			this.query = builder.createQuery(SalesPerPeriod.class);
			this.root = query.from(Order.class);
			this.predicates = getSalerPerPeriodPredicates(filter);
		}

		public void prepareQuery(CompoundSelection<SalesPerPeriod> selection, Expression<LocalDate> groupBy) {
			query.select(selection);
			query.where(predicates.toArray(new Predicate[0]));
			query.groupBy(groupBy);
		}

		private List<Predicate> getSalerPerPeriodPredicates(SalesPerPeriodFilter filter) {
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
}
