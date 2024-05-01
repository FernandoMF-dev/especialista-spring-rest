package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserInsertDto extends UserDto implements Serializable {
	@NotBlank(message = "user.password.not_blank")
	@Password(message = "user.password.invalid")
	private String password;

	public UserInsertDto() {
		super();
	}
}
