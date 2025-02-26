package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Data
@Embeddable
public class Address {
	@Column(name = "address_cep", length = 9)
	private String cep;

	@Column(name = "address_public_space", length = 100)
	private String publicSpace;

	@Column(name = "address_street_number", length = 20)
	private String streetNumber;

	@Column(name = "address_complement", length = 60)
	private String complement;

	@Column(name = "address_district", length = 60)
	private String district;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_city_id")
	private City city;
}
