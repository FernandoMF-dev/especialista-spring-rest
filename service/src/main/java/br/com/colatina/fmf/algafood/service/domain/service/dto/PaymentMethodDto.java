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

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(name = "Model <PaymentMethod>", description = "Representation model for payment method")
@Relation(collectionRelation = "payment_methods")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto extends RepresentationModel<PaymentMethodDto> implements Serializable {
	@Schema(description = "ID of the payment method", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Description of the payment method", example = "Credit Card")
	@NotBlank(message = "payment_method.description.not_blank")
	private String description;
}
