package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(name = "Model <OrderProduct (Input)>", description = "Representation model for the products included in a new order being emitted")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductInsertDto implements Serializable {
	@Schema(description = "Quantity of the product", example = "1")
	@Min(value = 1, message = "order_product_insert.quantity.min")
	@NotNull(message = "order_product_insert.quantity.not_null")
	private Integer quantity = 1;

	@Schema(description = "ID of the selected product", example = "1")
	@NotNull(message = "order_product_insert.product_id.not_null")
	private Long productId;

	@Schema(description = "Additional observation for the product", example = "No onion")
	private String observation;
}
