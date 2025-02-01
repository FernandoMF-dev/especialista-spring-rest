package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(name = "Model <User>", description = "Representation model for a user account")
@Relation(collectionRelation = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto extends RepresentationModel<AppUserDto> implements Serializable {
	@Schema(description = "ID of the user", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Name of the user", example = "John Doe")
	@NotBlank(message = "user.name.not_blank")
	private String name;

	@Schema(description = "Email of the user", example = "john.doe@example.com")
	@Email(message = "user.email.invalid")
	@NotBlank(message = "user.email.not_blank")
	private String email;
}
