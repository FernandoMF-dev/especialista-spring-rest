package br.com.colatina.fmf.algafood.service.domain.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductPictureDto {
	@NotNull(message = "file_upload.file.not_null")
	private MultipartFile file;
	@NotBlank(message = "file_upload.description.not_blank")
	private String description;
}
