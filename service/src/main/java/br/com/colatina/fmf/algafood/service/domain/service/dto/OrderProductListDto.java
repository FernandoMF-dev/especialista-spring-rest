package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class OrderProductListDto implements Serializable {
	private Long id;
	private Integer quantity;
	private String observation;
	private Long productId;
	private String productName;
	private Long orderId;
}
