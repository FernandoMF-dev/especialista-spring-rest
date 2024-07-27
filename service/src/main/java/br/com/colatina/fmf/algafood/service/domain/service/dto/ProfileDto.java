package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "Profile", description = "Representation model for profile")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto implements Serializable {
	@ApiModelProperty(value = "ID of the profile", example = "1")
	private Long id;

	@ApiModelProperty(value = "Name of the profile", example = "Admin", required = true)
	@NotBlank(message = "profile.name.not_blank")
	private String name;
}
