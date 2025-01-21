package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface StatisticsControllerDocumentation {
	HypermediaModel statistics();

	CollectionModel<SalesPerPeriod> findSalesPerDay(SalesPerPeriodFilter filter);

	ResponseEntity<byte[]> findSalesPerDayPdf(SalesPerPeriodFilter filter);

	CollectionModel<SalesPerPeriod> findSalesPerMonth(SalesPerPeriodFilter filter);

	CollectionModel<SalesPerPeriod> findSalesPerYear(SalesPerPeriodFilter filter);
}
