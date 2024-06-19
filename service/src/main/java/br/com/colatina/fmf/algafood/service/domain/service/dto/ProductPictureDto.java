package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPictureDto {
	private Long id;
	private String fileName;
	private String description;
	private String contentType;
	private Long size;
}
