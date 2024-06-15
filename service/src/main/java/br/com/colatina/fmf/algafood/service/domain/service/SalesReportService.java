package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;

public interface SalesReportService {
	byte[] findSalesPerDayPdf(SalesPerPeriodFilter filter);
}
