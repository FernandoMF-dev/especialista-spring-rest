package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@ApiModel(value = "Product", description = "Representation model for a product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
	@ApiModelProperty(value = "ID of the product", example = "1")
	private Long id;

	@ApiModelProperty(value = "Name of the product", example = "Cheeseburger", required = true)
	@NotBlank(message = "product.name.not_blank")
	private String name;

	@ApiModelProperty(value = "Description of the product", example = "A delicious cheeseburger", required = true)
	@NotNull(message = "product.description.not_null")
	private String description;

	@ApiModelProperty(value = "Price of the product", example = "9.99", required = true)
	@NotNull(message = "product.price.not_null")
	@PositiveOrZero(message = "product.price.positive_or_zero")
	private Double price;

	@ApiModelProperty(value = "Active status of the product", example = "true", required = true)
	@NotNull(message = "product.active.not_null")
	private Boolean active;

	@ApiModelProperty(value = "ID of the restaurant", example = "1")
	private Long restaurantId;

	@ApiModelProperty(value = "Name of the restaurant", example = "Burger King")
	private String restaurantName;
}
