package br.com.colatina.fmf.algafood.service.domain.service.statistics;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SalesPerPeriod implements Serializable {
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
