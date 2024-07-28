package br.com.colatina.fmf.algafood.service.domain.service.filter;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.OffsetDateTime;

@ApiModel(value = "Order (Filter)", description = "Filter model for paginating orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageFilter implements Serializable {
	@ApiModelProperty(value = "Status of the order", example = "DELIVERED")
	private OrderStatusEnum status;

	@ApiModelProperty(value = "ID of the restaurant", example = "1")
	private Long restaurantId;

	@ApiModelProperty(value = "ID of the client", example = "1")
	private Long clientId;

	@ApiModelProperty(value = "Minimum registration date for the order", example = "2023-01-01T00:00:00Z")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime minRegistrationDate;

	@ApiModelProperty(value = "Maximum registration date for the order", example = "2023-12-31T23:59:59Z")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime maxRegistrationDate;
}
