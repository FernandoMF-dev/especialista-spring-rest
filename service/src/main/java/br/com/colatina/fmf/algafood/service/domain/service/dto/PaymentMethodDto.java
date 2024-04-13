package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto implements Serializable {
	@NotNull(message = "payment_method.id.not_null", groups = ValidationGroups.RequiredPaymentMethod.class)
	private Long id;
	@NotBlank(message = "payment_method.description.not_blank")
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PaymentMethodDto)) {
			return false;
		}
		PaymentMethodDto that = (PaymentMethodDto) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
