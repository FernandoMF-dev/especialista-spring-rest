package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel(value = "OrderProduct", description = "Representation model for the products included in a registered order")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto implements Serializable {
	@ApiModelProperty(value = "ID of the product in the order", example = "1")
	private Long id;

	@ApiModelProperty(value = "Quantity of the product", example = "1")
	private Integer quantity = 1;

	@ApiModelProperty(value = "Unit price of the product", example = "10.00")
	private Double unitPrice;

	@ApiModelProperty(value = "Total price of the product", example = "10.00")
	private Double totalPrice;

	@ApiModelProperty(value = "Additional observation for the product", example = "No onion")
	private String observation;

	@ApiModelProperty(value = "ID of the order", example = "1")
	private Long orderId;

	@ApiModelProperty(value = "ID of the product", example = "1")
	private Long productId;

	@ApiModelProperty(value = "Name of the product", example = "Burger")
	private String productName;

	@ApiModelProperty(value = "Description of the product", example = "Delicious beef burger")
	private String productDescription;
}
