package br.com.colatina.fmf.algafood.service.domain.service.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

@ApiModel(value = "Filter <SalesPerPeriod>", description = "Filter model for sales statistics per period")
@Getter
@Setter
public class SalesPerPeriodFilter {
	@ApiModelProperty(value = "ID of the restaurant", example = "1")
	private Long restaurantId;

	@ApiModelProperty(value = "Start date of the period", example = "2023-01-01T00:00:00Z")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime startDate;

	@ApiModelProperty(value = "End date of the period", example = "2023-12-31T23:59:59Z")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime endDate;

	@ApiModelProperty(value = "Time offset for the period", example = "+03:00")
	@Pattern(regexp = "^([+-])(\\d{2})(:?\\d{2})?$", message = "statistics.time_offset.invalid_format")
	private String timeOffset = "+00:00";
}
