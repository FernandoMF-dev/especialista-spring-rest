package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.core.openapi.annotations.SalesPerPeriodFilterParameterDocs;
import br.com.colatina.fmf.algafood.service.domain.service.filter.SalesPerPeriodFilter;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = SpringDocControllerTags.STATISTICS)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface StatisticsControllerDocumentation {
	@Operation(summary = "Find available statistics endpoints")
	HypermediaModel statistics();

	@Operation(summary = "Find daily sales statistics")
	@ApiResponse(responseCode = "200", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CollectionModel.class)),
			@Content(mediaType = MediaType.APPLICATION_PDF_VALUE, schema = @Schema(type = "string", format = "binary")),
	})
	@SalesPerPeriodFilterParameterDocs
	CollectionModel<SalesPerPeriod> findSalesPerDay(@Parameter(hidden = true) SalesPerPeriodFilter filter);

	@Operation(summary = "Export daily sales statistics", hidden = true)
	@SalesPerPeriodFilterParameterDocs
	ResponseEntity<byte[]> findSalesPerDayPdf(@Parameter(hidden = true) SalesPerPeriodFilter filter);

	@Operation(summary = "Find monthly sales statistics")
	@SalesPerPeriodFilterParameterDocs
	CollectionModel<SalesPerPeriod> findSalesPerMonth(@Parameter(hidden = true) SalesPerPeriodFilter filter);

	@Operation(summary = "Find yearly sales statistics")
	@SalesPerPeriodFilterParameterDocs
	CollectionModel<SalesPerPeriod> findSalesPerYear(@Parameter(hidden = true) SalesPerPeriodFilter filter);
}
