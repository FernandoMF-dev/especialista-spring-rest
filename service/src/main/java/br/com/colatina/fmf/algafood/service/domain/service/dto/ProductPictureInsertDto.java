package br.com.colatina.fmf.algafood.service.domain.service.dto;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileContentType;
import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductPictureInsertDto {
	@NotNull(message = "file_upload.file.not_null")
	@FileSize(max = "500KB", message = "file_upload.file.max_size")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}, message = "file_upload.file.invalid_type.image")
	private MultipartFile file;

	@NotBlank(message = "file_upload.description.not_blank")
	private String description;
}
