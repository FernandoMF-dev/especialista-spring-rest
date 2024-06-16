package br.com.colatina.fmf.algafood.service.core.validation.validators;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileContentType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeConstraintValidator implements ConstraintValidator<FileContentType, MultipartFile> {
	private List<String> allowed;

	@Override
	public void initialize(FileContentType constraint) {
		this.allowed = Arrays.asList(constraint.allowed());
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile == null || this.allowed.contains(multipartFile.getContentType());
	}
}
