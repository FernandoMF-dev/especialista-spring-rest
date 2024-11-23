package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "Model <User (Create)>", description = "Representation model for registering a new user account")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUserInsertDto extends AppUserDto implements Serializable {
	@ApiModelProperty(value = "Password of the user", required = true)
	@NotBlank(message = "user.password.not_blank")
	@Password(message = "user.password.invalid")
	private String password;
}
