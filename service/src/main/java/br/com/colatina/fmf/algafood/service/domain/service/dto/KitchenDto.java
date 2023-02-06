package br.com.colatina.fmf.algafood.service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonRootName("kitchen")
@NoArgsConstructor
@AllArgsConstructor
public class KitchenDto implements Serializable {
	private Long id;
	@NotBlank
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof KitchenDto)) {
			return false;
		}
		KitchenDto that = (KitchenDto) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
