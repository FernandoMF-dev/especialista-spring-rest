package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(name = "Model <User (Create)>", description = "Representation model for registering a new user account")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
public class AppUserInsertDto extends AppUserDto implements Serializable {
	@Schema(description = "Password of the user")
	@NotBlank(message = "user.password.not_blank")
	@Password(message = "user.password.invalid")
	private String password;
}
