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

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(name = "Model <Profile>", description = "Representation model for profile")
@Relation(collectionRelation = "profiles")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto extends RepresentationModel<ProfileDto> implements Serializable {
	@Schema(description = "ID of the profile", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Name of the profile", example = "Admin")
	@NotBlank(message = "profile.name.not_blank")
	private String name;
}
