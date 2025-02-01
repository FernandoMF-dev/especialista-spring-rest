package br.com.colatina.fmf.algafood.service.core.validation.validators;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.FileSize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileSizeConstraintValidator implements ConstraintValidator<FileSize, MultipartFile> {
	private DataSize maxSize;

	@Override
	public void initialize(FileSize constraint) {
		this.maxSize = DataSize.parse(constraint.max());
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || value.getSize() <= maxSize.toBytes();
	}
}
