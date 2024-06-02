package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;

import java.util.List;

public interface SalesQueryService {
	List<SalesPerPeriod> findSalesPerDay(SalesPerPeriodFilter filter);

	List<SalesPerPeriod> findSalesPerMonth(SalesPerPeriodFilter filter);

	List<SalesPerPeriod> findSalesPerYear(SalesPerPeriodFilter filter);
}
