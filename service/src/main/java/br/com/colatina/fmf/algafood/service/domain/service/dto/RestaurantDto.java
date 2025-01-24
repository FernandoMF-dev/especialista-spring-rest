package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(name = "Model <Restaurant>", description = "Representation model for a restaurant")
@Relation(collectionRelation = "restaurants")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RestaurantDto extends RepresentationModel<RestaurantDto> implements Serializable {
	@Schema(description = "ID of the restaurant", example = "1")
	@EqualsAndHashCode.Include
	private Long id;

	@Schema(description = "Name of the restaurant", example = "Burger King")
	private String name;

	@Schema(description = "Freight fee of the restaurant", example = "5.00")
	private Double freightFee;

	@Schema(description = "Date which the restaurant was registered registered in the system", example = "2023-01-01T00:00:00Z")
	private OffsetDateTime registrationDate;

	@Schema(description = "Date which the information of the restaurant were last updated", example = "2023-01-01T00:00:00Z")
	private OffsetDateTime updateDate;

	@Schema(description = "Active status of the restaurant", example = "true")
	private Boolean active;

	@Schema(description = "Open status of the restaurant", example = "true")
	private Boolean open;

	@Schema(description = "Cuisine of the restaurant")
	private CuisineDto cuisine;

	@Schema(description = "List of products offered by the restaurant")
	private List<ProductDto> products = new ArrayList<>();

	@Schema(description = "Set of payment methods accepted by the restaurant")
	private Set<PaymentMethodDto> paymentMethods = new HashSet<>();

	@Schema(description = "Set of users responsible for the restaurant")
	private Set<AppUserDto> responsible = new HashSet<>();

	@Schema(description = "Address of the restaurant")
	private AddressDto address;
}
