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

import java.io.Serializable;

@Schema(name = "Model <Permission>", description = "Representation model for a permission")
@Relation(collectionRelation = "permissions")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto extends RepresentationModel<PermissionDto> implements Serializable {
	@Schema(description = "ID of the permission", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Name of the permission", example = "CREATE_CUISINE")
	private String name;
}
