package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDto implements Serializable {
	@NotNull
	private Long userId;
	@NotNull
	private Long restaurantId;
	@NotNull
	private Long paymentMethodId;
	@NotNull
	private Integer installments;
	@NotEmpty
	@Valid
	private List<OrderProductInsertDto> orderProducts = new ArrayList<>();
	@NotNull
	@Valid
	private AddressDto address;
}
