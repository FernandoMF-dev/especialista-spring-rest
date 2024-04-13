package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
	private Long id;
	@NotBlank(message = "user.name.not_blank")
	private String name;
	@Email(message = "user.email.invalid")
	@NotBlank(message = "user.email.not_blank")
	private String email;
	@NotBlank(message = "user.password.not_blank")
	private String password;
}
