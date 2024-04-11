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
	@NotBlank(message = "Name can not be null and must contain at least one non-whitespace character")
	private String name;
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email can not be null and must contain at least one non-whitespace character")
	private String email;
	@NotBlank(message = "Password can not be null and must contain at least one non-whitespace character")
	private String password;
}
