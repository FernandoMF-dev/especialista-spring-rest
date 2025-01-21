package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto extends RepresentationModel<OrderProductDto> implements Serializable {
	@EqualsAndHashCode.Include
	private Long id;

	private Integer quantity = 1;

	private Double unitPrice;

	private Double totalPrice;

	private String observation;

	private String orderCode;

	private Long productId;

	private String productName;

	private String productDescription;
}
