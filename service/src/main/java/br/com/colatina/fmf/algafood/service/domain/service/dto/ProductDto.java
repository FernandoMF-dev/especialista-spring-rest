package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Schema(name = "Model <Product>", description = "Representation model for a product")
@Relation(collectionRelation = "products")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends RepresentationModel<ProductDto> implements Serializable {
	@Schema(description = "ID of the product", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Name of the product", example = "Cheeseburger")
	@NotBlank(message = "product.name.not_blank")
	private String name;

	@Schema(description = "Description of the product", example = "A delicious cheeseburger")
	@NotNull(message = "product.description.not_null")
	private String description;

	@Schema(description = "Price of the product", example = "9.99")
	@NotNull(message = "product.price.not_null")
	@PositiveOrZero(message = "product.price.positive_or_zero")
	private Double price;

	@Schema(description = "Active status of the product", example = "true")
	@NotNull(message = "product.active.not_null")
	private Boolean active;

	@Schema(description = "ID of the restaurant", example = "1")
	private Long restaurantId;

	@Schema(description = "Name of the restaurant", example = "Burger King")
	private String restaurantName;
}
