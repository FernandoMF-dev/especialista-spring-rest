package br.com.colatina.fmf.algafood.service.domain.service.dto;

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

@Relation(collectionRelation = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto extends RepresentationModel<AppUserDto> implements Serializable {
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank(message = "user.name.not_blank")
	private String name;

	@Email(message = "user.email.invalid")
	@NotBlank(message = "user.email.not_blank")
	private String email;
}
