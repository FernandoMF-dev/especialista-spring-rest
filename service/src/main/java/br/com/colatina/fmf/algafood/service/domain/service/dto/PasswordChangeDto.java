package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "User (Password Change)", description = "Representation model for changing a user's password")
@Getter
@Setter
@ToString
public class PasswordChangeDto implements Serializable {

	@ApiModelProperty(value = "Current password of the user", name = "current", required = true)
	@JsonProperty("current")
	@NotBlank(message = "password_change.current.not_blank")
	private String currentPassword;

	@ApiModelProperty(value = "New password of the user", name = "new", required = true)
	@JsonProperty("new")
	@Password(message = "password_change.new.invalid")
	private String newPassword;
}
