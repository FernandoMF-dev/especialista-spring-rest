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

@ApiModel(value = "Model <PaymentMethod>", description = "Representation model for payment method")
@Relation(collectionRelation = "payment_methods")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto extends RepresentationModel<PaymentMethodDto> implements Serializable {
	@ApiModelProperty(value = "ID of the payment method", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@ApiModelProperty(value = "Description of the payment method", example = "Credit Card", required = true)
	@NotBlank(message = "payment_method.description.not_blank")
	private String description;
}
