package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.SalesReportService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {
	private final SalesQueryService salesQueryService;
	private final SalesReportService salesReportService;

	@GetMapping(path = "/sales-per-day")
	public ResponseEntity<List<SalesPerPeriod>> findSalesPerDay(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to find all daily sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		return new ResponseEntity<>(salesQueryService.findSalesPerDay(filter), HttpStatus.OK);
	}

	@GetMapping(path = "/sales-per-day", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> findSalesPerDayPdf(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to export to PDF the report with all daily sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		byte[] bytesPdf = salesReportService.findSalesPerDayPdf(filter);
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sales-per-day.pdf");

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}

	@GetMapping("/sales-per-month")
	public ResponseEntity<List<SalesPerPeriod>> findSalesPerMonth(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to find all monthly sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		return new ResponseEntity<>(salesQueryService.findSalesPerMonth(filter), HttpStatus.OK);
	}

	@GetMapping("/sales-per-year")
	public ResponseEntity<List<SalesPerPeriod>> findSalesPerYear(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to find all yearly sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		return new ResponseEntity<>(salesQueryService.findSalesPerYear(filter), HttpStatus.OK);
	}
}
