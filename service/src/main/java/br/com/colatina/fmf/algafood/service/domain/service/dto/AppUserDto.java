package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "Model <User>", description = "Representation model for a user account")
@Relation(collectionRelation = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto extends RepresentationModel<AppUserDto> implements Serializable {
	@ApiModelProperty(value = "ID of the user", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@ApiModelProperty(value = "Name of the user", example = "John Doe", required = true)
	@NotBlank(message = "user.name.not_blank")
	private String name;

	@ApiModelProperty(value = "Email of the user", example = "john.doe@example.com", required = true)
	@Email(message = "user.email.invalid")
	@NotBlank(message = "user.email.not_blank")
	private String email;
}
