package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "User", description = "Representation model for a user account")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
	@ApiModelProperty(value = "ID of the user", example = "1")
	private Long id;

	@ApiModelProperty(value = "Name of the user", example = "John Doe", required = true)
	@NotBlank(message = "user.name.not_blank")
	private String name;

	@ApiModelProperty(value = "Email of the user", example = "john.doe@example.com", required = true)
	@Email(message = "user.email.invalid")
	@NotBlank(message = "user.email.not_blank")
	private String email;
}
