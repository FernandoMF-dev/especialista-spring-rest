package br.com.colatina.fmf.algafood.service.api.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = SpringFoxControllerTags.STATISTICS)
public interface StatisticsControllerDocumentation {
	@ApiOperation("Find daily sales statistics")
	@ApiResponse(responseCode = "200", description = "Daily sales statistics retrieved")
	ResponseEntity<List<SalesPerPeriod>> findSalesPerDay(@ApiParam(value = "Filter for sales per period", required = true) SalesPerPeriodFilter filter);

	@ApiOperation("Export daily sales statistics")
	@ApiResponse(responseCode = "200", description = "PDF report generated")
	ResponseEntity<byte[]> findSalesPerDayPdf(@ApiParam(value = "Filter for sales per period", required = true) SalesPerPeriodFilter filter);

	@ApiOperation("Find monthly sales statistics")
	@ApiResponse(responseCode = "200", description = "Monthly sales statistics retrieved")
	ResponseEntity<List<SalesPerPeriod>> findSalesPerMonth(@ApiParam(value = "Filter for sales per period", required = true) SalesPerPeriodFilter filter);

	@ApiOperation("Find yearly sales statistics")
	@ApiResponse(responseCode = "200", description = "Yearly sales statistics retrieved")
	ResponseEntity<List<SalesPerPeriod>> findSalesPerYear(@ApiParam(value = "Filter for sales per period", required = true) SalesPerPeriodFilter filter);
}
