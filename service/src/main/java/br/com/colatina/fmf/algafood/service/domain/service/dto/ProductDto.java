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
	@NotBlank(message = "Name can not be null and must contain at least one non-whitespace character")
	private String name;
	@NotNull(message = "Description can not be null")
	private String description;
	@NotNull(message = "Price can not be null")
	@PositiveOrZero(message = "Price can not be lower than zero")
	private Double price;
	@NotNull(message = "The active field can not be null")
	private Boolean active;
	@NotNull(message = "Must specify the id of the restaurant offering the product")
	private Long restaurantId;
	private String restaurantName;
}
