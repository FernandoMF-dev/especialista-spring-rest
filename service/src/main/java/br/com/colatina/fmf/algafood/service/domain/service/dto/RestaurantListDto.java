package br.com.colatina.fmf.algafood.service.domain.service.dto;

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
public class RestaurantListDto implements Serializable {
	private Long id;
	private String name;
	private Double freightFee;
	private Boolean active;
	private Long kitchenId;
	private String kitchenName;
}
