package br.com.colatina.fmf.algafood.service.infrastructure.service.report;

import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.SalesReportService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.ReportException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PdfSalesReportServiceImpl implements SalesReportService {

	private final SalesQueryService salesQueryService;

	@Override
	public byte[] findSalesPerDayPdf(SalesPerPeriodFilter filter) {
		try {
			var inputStream = getClass().getResourceAsStream("/reports/sales-per-day.jasper");

			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var source = salesQueryService.findSalesPerDay(filter);
			var dataSource = new JRBeanCollectionDataSource(source);

			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("error.report.daily_sales", e);
		}
	}
}
