package br.com.colatina.fmf.algafood.service.infrastructure.service.query;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.Order_;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant_;
import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SalesQueryServiceImpl implements SalesQueryService {
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SalesPerPeriod> findSalesPerDay(SalesPerPeriodFilter filter) {
		SalesPerPeriodCriteria criteria = new SalesPerPeriodCriteria(filter);

		var fnDateRegistrationDate = CriteriaFunctionCreator.fnDateRegistrationDate(criteria.cb, criteria.root, filter, DAY);
		var fnYearRegistrationDate = CriteriaFunctionCreator.fnYearRegistrationDate(criteria.cb, fnDateRegistrationDate);
		var fnMonthRegistrationDate = CriteriaFunctionCreator.fnMonthRegistrationDate(criteria.cb, fnDateRegistrationDate);
		var fnDayRegistrationDate = CriteriaFunctionCreator.fnDayRegistrationDate(criteria.cb, fnDateRegistrationDate);

		var selection = criteria.cb.construct(SalesPerPeriod.class,
				criteria.cb.count(criteria.root.get(Order_.ID)),
				criteria.cb.sum(criteria.root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate,
				fnMonthRegistrationDate,
				fnDayRegistrationDate
		);

		criteria.prepareQuery(selection, fnDateRegistrationDate);
		return entityManager.createQuery(criteria.query).getResultList();
	}

	@Override
	public List<SalesPerPeriod> findSalesPerMonth(SalesPerPeriodFilter filter) {
		SalesPerPeriodCriteria criteria = new SalesPerPeriodCriteria(filter);

		var fnDateRegistrationDate = CriteriaFunctionCreator.fnDateRegistrationDate(criteria.cb, criteria.root, filter, MONTH);
		var fnYearRegistrationDate = CriteriaFunctionCreator.fnYearRegistrationDate(criteria.cb, fnDateRegistrationDate);
		var fnMonthRegistrationDate = CriteriaFunctionCreator.fnMonthRegistrationDate(criteria.cb, fnDateRegistrationDate);

		var selection = criteria.cb.construct(SalesPerPeriod.class,
				criteria.cb.count(criteria.root.get(Order_.ID)),
				criteria.cb.sum(criteria.root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate,
				fnMonthRegistrationDate
		);

		criteria.prepareQuery(selection, fnDateRegistrationDate);
		return entityManager.createQuery(criteria.query).getResultList();
	}

	@Override
	public List<SalesPerPeriod> findSalesPerYear(SalesPerPeriodFilter filter) {
		SalesPerPeriodCriteria criteria = new SalesPerPeriodCriteria(filter);

		var fnDateRegistrationDate = CriteriaFunctionCreator.fnDateRegistrationDate(criteria.cb, criteria.root, filter, YEAR);
		var fnYearRegistrationDate = CriteriaFunctionCreator.fnYearRegistrationDate(criteria.cb, fnDateRegistrationDate);

		var selection = criteria.cb.construct(SalesPerPeriod.class,
				criteria.cb.count(criteria.root.get(Order_.ID)),
				criteria.cb.sum(criteria.root.get(Order_.TOTAL_VALUE)),
				fnYearRegistrationDate
		);

		criteria.prepareQuery(selection, fnDateRegistrationDate);
		return entityManager.createQuery(criteria.query).getResultList();
	}

	private class SalesPerPeriodCriteria {
		public final CriteriaBuilder cb;
		public final CriteriaQuery<SalesPerPeriod> query;
		public final Root<Order> root;
		public final List<Predicate> predicates;

		public SalesPerPeriodCriteria(SalesPerPeriodFilter filter) {
			this.cb = entityManager.getCriteriaBuilder();
			this.query = cb.createQuery(SalesPerPeriod.class);
			this.root = query.from(Order.class);
			this.predicates = getSalesPerPeriodPredicates(filter);
		}

		public void prepareQuery(CompoundSelection<SalesPerPeriod> selection, Expression<Timestamp> groupBy) {
			query.select(selection);
			query.where(predicates.toArray(new Predicate[0]));
			query.groupBy(groupBy);
		}

		private List<Predicate> getSalesPerPeriodPredicates(SalesPerPeriodFilter filter) {
			List<Predicate> predicateList = new ArrayList<>();

			predicateList.add(root.get(Order_.STATUS).in(OrderStatusEnum.CONFIRMED, OrderStatusEnum.DELIVERED));

			if (Objects.nonNull(filter.getRestaurantId())) {
				predicateList.add(cb.equal(root.get(Order_.RESTAURANT).get(Restaurant_.ID), filter.getRestaurantId()));
			}

			if (Objects.nonNull(filter.getStartDate())) {
				predicateList.add(cb.greaterThanOrEqualTo(root.get(Order_.REGISTRATION_DATE), filter.getStartDate()));
			}

			if (Objects.nonNull(filter.getEndDate())) {
				predicateList.add(cb.lessThanOrEqualTo(root.get(Order_.REGISTRATION_DATE), filter.getEndDate()));
			}

			return predicateList;
		}
	}

	private static class CriteriaFunctionCreator {
		private static final String FN_DATE_TRUNC = "date_trunc";
		private static final String FN_TIME_ZONE = "timezone";
		public static final String FN_DATE_PART = "date_part";

		public static Expression<Timestamp> fnDateRegistrationDate(CriteriaBuilder cb, Root<Order> root, SalesPerPeriodFilter filter, String dateTruncParam) {
			var fnDateRegistrationDate = cb.function(FN_TIME_ZONE, Timestamp.class, cb.literal(filter.getTimeOffset()), root.get(Order_.REGISTRATION_DATE));
			return cb.function(FN_DATE_TRUNC, Timestamp.class, cb.literal(dateTruncParam), fnDateRegistrationDate);
		}

		public static Expression<Integer> fnYearRegistrationDate(CriteriaBuilder cb, Expression<Timestamp> fnDateRegistrationDate) {
			return cb.function(FN_DATE_PART, Integer.class, cb.literal(YEAR), fnDateRegistrationDate);
		}

		public static Expression<Integer> fnMonthRegistrationDate(CriteriaBuilder cb, Expression<Timestamp> fnDateRegistrationDate) {
			return cb.function(FN_DATE_PART, Integer.class, cb.literal(MONTH), fnDateRegistrationDate);
		}

		public static Expression<Integer> fnDayRegistrationDate(CriteriaBuilder cb, Expression<Timestamp> fnDateRegistrationDate) {
			return cb.function(FN_DATE_PART, Integer.class, cb.literal(DAY), fnDateRegistrationDate);
		}
	}
}
