package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class Address {
	@Column(name = "address_cep")
	private String cep;

	@Column(name = "address_public_space")
	private String publicSpace;

	@Column(name = "address_street_number")
	private String streetNumber;

	@Column(name = "address_complement")
	private String complement;

	@Column(name = "address_district")
	private String district;

	@ManyToOne
	@JoinColumn(name = "address_city_id")
	private City city;
}
