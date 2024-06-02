package br.com.colatina.fmf.algafood.service.infrastructure.service;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.Order_;
import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SalesQueryServiceImpl implements SalesQueryService {
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";

	private static final String FN_DATE_TRUNC = "date_trunc";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SalesPerPeriod> findSalesPerDay(SalesPerPeriodFilter filter) {
		var builder = entityManager.getCriteriaBuilder();
		var query = builder.createQuery(SalesPerPeriod.class);
		var root = query.from(Order.class);

		var fnDateRegistrationDate = builder.function(FN_DATE_TRUNC, LocalDate.class, builder.literal(DAY), root.get(Order_.REGISTRATION_DATE));
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
}
