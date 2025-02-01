package br.com.colatina.fmf.algafood.service.domain.service.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Pattern;
import java.time.OffsetDateTime;

@Getter
@Setter
public class SalesPerPeriodFilter {
	private Long restaurantId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime endDate;

	@Pattern(regexp = "^([+-])(\\d{2})(:?\\d{2})?$", message = "statistics.time_offset.invalid_format")
	private String timeOffset = "+00:00";
}
