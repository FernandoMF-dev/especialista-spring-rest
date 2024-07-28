package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "OrderProduct (Input)", description = "Representation model for the products included in a new order being emitted")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductInsertDto implements Serializable {
	@ApiModelProperty(value = "Quantity of the product", example = "1", required = true)
	@Min(value = 1, message = "order_product_insert.quantity.min")
	@NotNull(message = "order_product_insert.quantity.not_null")
	private Integer quantity = 1;

	@ApiModelProperty(value = "ID of the selected product", example = "1", required = true)
	@NotNull(message = "order_product_insert.product_id.not_null")
	private Long productId;

	@ApiModelProperty(value = "Additional observation for the product", example = "No onion")
	private String observation;
}
