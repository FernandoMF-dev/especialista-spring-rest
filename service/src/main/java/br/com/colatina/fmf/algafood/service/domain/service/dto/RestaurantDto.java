package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RestaurantDto extends RepresentationModel<RestaurantDto> implements Serializable {
	@EqualsAndHashCode.Include
	private Long id;

	private String name;

	private Double freightFee;

	private OffsetDateTime registrationDate;

	private OffsetDateTime updateDate;

	private Boolean active;

	private Boolean open;

	private CuisineDto cuisine;

	private List<ProductDto> products = new ArrayList<>();

	private Set<PaymentMethodDto> paymentMethods = new HashSet<>();

	private Set<AppUserDto> responsible = new HashSet<>();

	private AddressDto address;
}
