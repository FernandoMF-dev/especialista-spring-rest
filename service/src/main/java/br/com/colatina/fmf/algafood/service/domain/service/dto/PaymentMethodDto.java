package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "PaymentMethod", description = "Representation model for payment method")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto implements Serializable {
	@ApiModelProperty(value = "ID of the payment method", example = "1")
	private Long id;

	@ApiModelProperty(value = "Description of the payment method", example = "Credit Card", required = true)
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
