package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import br.com.colatina.fmf.algafood.service.core.validation.constraints.Cep;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;

@ApiModel(value = "Address", description = "Representation model for an address")
@Relation(collectionRelation = "addresses")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto extends RepresentationModel<AddressDto> implements Serializable {
	@ApiModelProperty(value = "Postal code of the address", example = "12345-678", required = true)
	@NotBlank(message = "address.cep.not_blank")
	@Cep(message = "address.cep.invalid")
	private String cep;

	@ApiModelProperty(value = "Public space of the address", example = "Main Street", required = true)
	@NotBlank(message = "address.public_space.not_blank")
	private String publicSpace;

	@ApiModelProperty(value = "Street number of the address", example = "123", required = true)
	@NotBlank(message = "address.street_number.not_blank")
	private String streetNumber;

	@ApiModelProperty(value = "Complement of the address", example = "Apt 101")
	private String complement;

	@ApiModelProperty(value = "District of the address", example = "Downtown", required = true)
	@NotBlank(message = "address.district.not_blank")
	private String district;

	@ApiModelProperty(value = "City of the address", required = true)
	@NotNull(message = "address.city.not_null")
	@ConvertGroup(to = ValidationGroups.RequiredCity.class)
	@Valid
	private CityDto city;
}
