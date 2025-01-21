package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUserInsertDto extends AppUserDto implements Serializable {
	@NotBlank(message = "user.password.not_blank")
	@Password(message = "user.password.invalid")
	private String password;
}
