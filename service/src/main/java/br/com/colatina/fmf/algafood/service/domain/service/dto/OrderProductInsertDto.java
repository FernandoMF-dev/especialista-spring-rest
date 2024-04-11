package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductInsertDto implements Serializable {
	@Min(value = 1, message = "The quantity of a product in an order cannot be less than 1")
	@NotNull(message = "The quantity of a product in an order cannot be null")
	private Integer quantity = 1;
	@NotNull(message = "Must specify the id of the product included in the order")
	private Long productId;
	private String observation;
}
