package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Schema(name = "Model <OrderProduct>", description = "Representation model for the products included in a registered order")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto extends RepresentationModel<OrderProductDto> implements Serializable {
	@Schema(description = "ID of the product in the order", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Quantity of the product", example = "1")
	private Integer quantity = 1;

	@Schema(description = "Unit price of the product", example = "10.00")
	private Double unitPrice;

	@Schema(description = "Total price of the product", example = "10.00")
	private Double totalPrice;

	@Schema(description = "Additional observation for the product", example = "No onion")
	private String observation;

	@Schema(description = "Unique code of the order", example = "123e4567-e89b-12d3-a456-426614174000")
	private String orderCode;

	@Schema(description = "ID of the product", example = "1")
	private Long productId;

	@Schema(description = "Name of the product", example = "Burger")
	private String productName;

	@Schema(description = "Description of the product", example = "Delicious beef burger")
	private String productDescription;
}
