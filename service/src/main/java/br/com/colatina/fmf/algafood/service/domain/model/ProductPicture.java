package br.com.colatina.fmf.algafood.service.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "tb_product_picture")
@Entity
public class ProductPicture {
	@Id
	@Column(name = "product_id", nullable = false)
	private Long id;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "content_type", nullable = false)
	private String contentType;

	@Column(name = "size", nullable = false)
	private Long size;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Product product;
}
