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
public class OrderProductDto implements Serializable {
	private Long id;
	private Integer quantity = 1;
	private Double unitPrice;
	private Double totalPrice;
	private String observation;

	private Long orderId;

	private Long productId;
	private String productName;
	private String productDescription;
}
