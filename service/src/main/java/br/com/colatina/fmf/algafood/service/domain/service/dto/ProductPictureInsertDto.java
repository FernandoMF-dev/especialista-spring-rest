package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileContentType;
import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileSize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "Product Picture (Input)", description = "Representation model for inserting a product picture")
@Getter
@Setter
public class ProductPictureInsertDto {
	@ApiModelProperty(value = "File of the product picture", required = true)
	@NotNull(message = "file_upload.file.not_null")
	@FileSize(max = "500KB", message = "file_upload.file.max_size")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}, message = "file_upload.file.invalid_type.image")
	private MultipartFile file;

	@ApiModelProperty(value = "Description of the product picture", example = "A picture of the product", required = true)
	@NotBlank(message = "file_upload.description.not_blank")
	private String description;
}
