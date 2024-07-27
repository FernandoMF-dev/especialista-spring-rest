package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "Cuisine", description = "Representation model for cuisine")
@Getter
@Setter
@ToString
@JsonRootName("cuisine")
@NoArgsConstructor
@AllArgsConstructor
public class CuisineDto implements Serializable {
	@ApiModelProperty(value = "ID of the cuisine", example = "1")
	private Long id;

	@ApiModelProperty(value = "Name of the cuisine", example = "Italian", required = true)
	@NotBlank(message = "cuisine.name.not_blank")
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CuisineDto)) {
			return false;
		}
		CuisineDto that = (CuisineDto) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
