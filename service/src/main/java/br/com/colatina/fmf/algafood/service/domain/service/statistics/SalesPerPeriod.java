package br.com.colatina.fmf.algafood.service.domain.service.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Relation(collectionRelation = "sales_per_period")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesPerPeriod extends RepresentationModel<SalesPerPeriod> implements Serializable {
	private Long totalSales;

	private Double totalRevenue;

	private Integer year;

	private Integer month;

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
