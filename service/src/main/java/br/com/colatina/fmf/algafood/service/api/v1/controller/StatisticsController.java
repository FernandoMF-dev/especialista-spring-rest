package br.com.colatina.fmf.algafood.service.api.v1.controller;

import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.StatisticsControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.hateoas.StatisticsHateoas;
import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.core.security.CheckSecurity;
import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.SalesReportService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController implements StatisticsControllerDocumentation {
	private final SalesQueryService salesQueryService;
	private final SalesReportService salesReportService;
	private final StatisticsHateoas statisticsHateoas;

	@GetMapping
	@Override
	@CheckSecurity.Public
	public HypermediaModel statistics() {
		log.debug("REST request to get the available statistics endpoints");
		return statisticsHateoas.getRootHypermediaModel();
	}

	@GetMapping(path = "/sales-per-day")
	@Override
	@CheckSecurity.Statistics.EmitSalesReport
	public ResponseEntity<CollectionModel<SalesPerPeriod>> findSalesPerDay(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to find all daily sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		List<SalesPerPeriod> result = salesQueryService.findSalesPerDay(filter);
		return new ResponseEntity<>(statisticsHateoas.mapSalesPerPeriodModel(result, "sales-per-day"), HttpStatus.OK);
	}

	@GetMapping(path = "/sales-per-day", produces = MediaType.APPLICATION_PDF_VALUE)
	@Override
	@CheckSecurity.Statistics.EmitSalesReport
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
	@Override
	@CheckSecurity.Statistics.EmitSalesReport
	public ResponseEntity<CollectionModel<SalesPerPeriod>> findSalesPerMonth(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to find all monthly sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		List<SalesPerPeriod> result = salesQueryService.findSalesPerMonth(filter);
		return new ResponseEntity<>(statisticsHateoas.mapSalesPerPeriodModel(result, "sales-per-month"), HttpStatus.OK);
	}

	@GetMapping("/sales-per-year")
	@Override
	@CheckSecurity.Statistics.EmitSalesReport
	public ResponseEntity<CollectionModel<SalesPerPeriod>> findSalesPerYear(@Valid SalesPerPeriodFilter filter) {
		log.debug("REST request to find all yearly sales statistics for restaurant {} between dates {} and {}, with a time offset of {}",
				filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate(), filter.getTimeOffset());

		List<SalesPerPeriod> result = salesQueryService.findSalesPerYear(filter);
		return new ResponseEntity<>(statisticsHateoas.mapSalesPerPeriodModel(result, "sales-per-year"), HttpStatus.OK);
	}
}
