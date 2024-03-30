package br.com.colatina.fmf.algafood.service.domain.service.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPageFilter implements Serializable {
	private String name;
	private Double minFreightFee;
	private Double maxFreightFee;
	private Boolean active;
	private Long kitchenId;
}
