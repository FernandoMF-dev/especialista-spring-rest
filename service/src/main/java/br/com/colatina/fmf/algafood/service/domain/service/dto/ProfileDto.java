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

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "Profile", description = "Representation model for profile")
@Relation(collectionRelation = "profiles")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto extends RepresentationModel<ProfileDto> implements Serializable {
	@ApiModelProperty(value = "ID of the profile", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@ApiModelProperty(value = "Name of the profile", example = "Admin", required = true)
	@NotBlank(message = "profile.name.not_blank")
	private String name;
}
