package br.com.colatina.fmf.algafood.service.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "tb_restaurant")
@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_restaurant")
	@SequenceGenerator(name = "seq_restaurant", allocationSize = 1, sequenceName = "seq_restaurant")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String nome;

	@Column(name = "freight_rate")
	private Double freightRate;

	@Column(name = "excluded")
	private Boolean excluido = Boolean.FALSE;

}
