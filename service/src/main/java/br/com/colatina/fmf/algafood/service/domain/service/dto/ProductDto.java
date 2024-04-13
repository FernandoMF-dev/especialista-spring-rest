package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
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
	@NotNull(message = "product.restaurant_id.not_null")
	private Long restaurantId;
	private String restaurantName;
}
