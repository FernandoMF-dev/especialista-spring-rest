package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
	@NotBlank
	@Pattern(regexp = "^\\d{5}-\\d{3}$")
	private String cep;
	@NotBlank
	private String publicSpace;
	@NotBlank
	private String streetNumber;
	private String complement;
	@NotBlank
	private String district;
	@NotNull
	private CityDto city;
}
