package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
	@NotBlank(message = "CEP can not be null and must contain at least one non-whitespace character")
	@Size(min = 9, max = 9, message = "The CEP field must be exactly 9 characters long")
	@Pattern(regexp = "^\\d{5}-\\d{3}$", message = "The CEP field must match the format '00000-000' where '0' is a numerical value")
	private String cep;
	@NotBlank(message = "The public space can not be null and must contain at least one non-whitespace character")
	private String publicSpace;
	@NotBlank(message = "The street number can not be null and must contain at least one non-whitespace character")
	private String streetNumber;
	private String complement;
	@NotBlank(message = "The district can not be null and must contain at least one non-whitespace character")
	private String district;
	@NotNull(message = "The city can not be null")
	private CityDto city;
}
