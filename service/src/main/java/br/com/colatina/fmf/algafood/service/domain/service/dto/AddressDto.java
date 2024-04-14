package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import br.com.colatina.fmf.algafood.service.core.validation.constraints.Cep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
	@NotBlank(message = "address.cep.not_blank")
	@Cep(message = "address.cep.invalid")
	private String cep;
	@NotBlank(message = "address.public_space.not_blank")
	private String publicSpace;
	@NotBlank(message = "address.street_number.not_blank")
	private String streetNumber;
	private String complement;
	@NotBlank(message = "address.district.not_blank")
	private String district;
	@NotNull(message = "address.city.not_null")
	@ConvertGroup(to = ValidationGroups.RequiredCity.class)
	@Valid
	private CityDto city;
}
