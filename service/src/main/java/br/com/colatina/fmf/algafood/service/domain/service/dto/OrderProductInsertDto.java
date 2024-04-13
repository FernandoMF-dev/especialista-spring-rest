package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductInsertDto implements Serializable {
	@Min(value = 1)
	@NotNull
	private Integer quantity = 1;
	@NotNull
	private Long productId;
	private String observation;
}
