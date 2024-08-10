package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value = "Product Picture", description = "Representation model for a product picture")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPictureDto {
	@ApiModelProperty(value = "ID of the product picture", example = "1")
	private Long id;

	@ApiModelProperty(value = "File name of the product picture", example = "picture.jpg", required = true)
	private String fileName;

	@ApiModelProperty(value = "Description of the product picture", example = "A picture of the product", required = true)
	private String description;

	@ApiModelProperty(value = "Content type of the product picture", example = "image/jpeg", required = true)
	private String contentType;

	@ApiModelProperty(value = "Size of the product picture in bytes", example = "204800", required = true)
	private Long size;
}
