package br.com.colatina.fmf.algafood.service.domain.service.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Schema(name = "Model <SalesPerPeriod>", description = "Representation model for sales statistics per period")
@Relation(collectionRelation = "sales_per_period")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesPerPeriod extends RepresentationModel<SalesPerPeriod> implements Serializable {
	@Schema(description = "Total number of sales", example = "100")
	private Long totalSales;

	@Schema(description = "Total revenue from sales", example = "1000.00")
	private Double totalRevenue;

	@Schema(description = "Year of the sales period", example = "2023")
	private Integer year;

	@Schema(description = "Month of the sales period (only for monthly and daily statistics)", example = "10", allowableValues = "range[1, 12]")
	private Integer month;

	@Schema(description = "Day of the sales period (only for daily statistics)", example = "05", allowableValues = "range[1, 31]")
	private Integer day;

	public SalesPerPeriod(Long totalSales, Double totalRevenue, Integer year) {
		this.totalSales = totalSales;
		this.totalRevenue = totalRevenue;
		this.year = year;
	}

	public SalesPerPeriod(Long totalSales, Double totalRevenue, Integer year, Integer month) {
		this(totalSales, totalRevenue, year);
		this.month = month;
	}

	public SalesPerPeriod(Long totalSales, Double totalRevenue, Integer year, Integer month, Integer day) {
		this(totalSales, totalRevenue, year, month);
		this.day = day;
	}
}
