package br.com.colatina.fmf.algafood.service.domain.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.http.MediaType;

import java.io.Serializable;

@Schema(name = "Model <ProductPicture>", description = "Representation model for a product picture")
@Relation(collectionRelation = "product_pictures")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ProductPictureDto extends RepresentationModel<ProductPictureDto> implements Serializable {
	@Schema(description = "ID of the product picture", example = "1")
	private Long id;

	@Schema(description = "File name of the product picture", example = "picture.jpg")
	private String fileName;

	@Schema(description = "Description of the product picture", example = "A picture of the product")
	private String description;

	@Schema(description = "Content type of the product picture", example = MediaType.IMAGE_JPEG_VALUE)
	private String contentType;

	@Schema(description = "Size of the product picture in bytes", example = "204800")
	private Long size;
}
