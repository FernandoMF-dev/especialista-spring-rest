package br.com.colatina.fmf.algafood.service.domain.service.dto;

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

@Relation(collectionRelation = "products")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends RepresentationModel<ProductDto> implements Serializable {
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank(message = "product.name.not_blank")
	private String name;

	@NotNull(message = "product.description.not_null")
	private String description;

	@NotNull(message = "product.price.not_null")
	@PositiveOrZero(message = "product.price.positive_or_zero")
	private Double price;

	@NotNull(message = "product.active.not_null")
	private Boolean active;

	private Long restaurantId;

	private String restaurantName;
}
