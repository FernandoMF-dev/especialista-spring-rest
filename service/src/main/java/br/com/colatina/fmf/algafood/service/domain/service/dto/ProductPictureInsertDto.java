package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileContentType;
import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Model <ProductPicture (Input)>", description = "Representation model for inserting a product picture")
@Getter
@Setter
public class ProductPictureInsertDto {
	@Schema(description = "File of the product picture (.jpg | .png")
	@NotNull(message = "file_upload.file.not_null")
	@FileSize(max = "500KB", message = "file_upload.file.max_size")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}, message = "file_upload.file.invalid_type.image")
	private MultipartFile file;

	@Schema(description = "Description of the product picture", example = "A picture of the product")
	@NotBlank(message = "file_upload.description.not_blank")
	private String description;
}
