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

import java.io.Serializable;

@ApiModel(value = "Permission", description = "Representation model for a permission")
@Relation(collectionRelation = "permissions")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto extends RepresentationModel<PermissionDto> implements Serializable {
	@ApiModelProperty(value = "ID of the permission", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@ApiModelProperty(value = "Name of the permission", example = "CREATE_CUISINE", required = true)
	private String name;

	@ApiModelProperty(value = "Description of the permission", example = "Permission to create a new cuisine", required = true)
	private String description;
}
