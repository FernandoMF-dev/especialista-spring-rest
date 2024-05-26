package br.com.colatina.fmf.algafood.service.domain.service.filter;

import br.com.colatina.fmf.algafood.service.domain.model.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageFilter implements Serializable {
	private OrderStatusEnum status;
	private Long restaurantId;
	private Long clientId;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime minRegistrationDate;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime maxRegistrationDate;
}
