package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class PasswordChangeDto implements Serializable {
	@JsonProperty("current")
	@NotBlank(message = "password_change.current.not_blank")
	private String currentPassword;
	@JsonProperty("new")
	@Password(message = "password_change.new.invalid")
	private String newPassword;
}
