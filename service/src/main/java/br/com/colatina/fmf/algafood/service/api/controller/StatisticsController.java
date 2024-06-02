package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.domain.service.SalesQueryService;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	private final SalesQueryService salesQueryService;

	@GetMapping("/sales-per-day")
	public ResponseEntity<List<SalesPerPeriod>> findSalesPerDay(SalesPerPeriodFilter filter) {
		log.debug("REST request to find all daily sales statistics for restaurant {} between dates {} and {}", filter.getRestaurantId(), filter.getStartDate(), filter.getEndDate());
		return new ResponseEntity<>(salesQueryService.findSalesPerDay(filter), HttpStatus.OK);
	}

}
