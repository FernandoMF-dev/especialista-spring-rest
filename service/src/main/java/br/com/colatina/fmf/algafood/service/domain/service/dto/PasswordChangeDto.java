package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(name = "Model <PasswordChange>", description = "Representation model for changing a user's password")
@Getter
@Setter
@ToString
public class PasswordChangeDto implements Serializable {
	@Schema(description = "Current password of the user", name = "current")
	@JsonProperty("current")
	@NotBlank(message = "password_change.current.not_blank")
	private String currentPassword;

	@Schema(description = "New password of the user", name = "new")
	@JsonProperty("new")
	@Password(message = "password_change.new.invalid")
	private String newPassword;
}
