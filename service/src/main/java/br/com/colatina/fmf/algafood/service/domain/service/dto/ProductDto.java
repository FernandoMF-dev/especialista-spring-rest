package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Double price;
	@NotNull
	private Boolean active;
	@NotNull
	private Long restaurantId;
	private String restaurantName;
}
