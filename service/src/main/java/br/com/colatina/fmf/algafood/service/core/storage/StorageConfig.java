package br.com.colatina.fmf.algafood.service.core.storage;

import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.infrastructure.service.storage.AwsS3FileStorageServiceImpl;
import br.com.colatina.fmf.algafood.service.infrastructure.service.storage.LocalFileStorageServiceImpl;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
	@Bean
	public FileStorageService fileStorageService(StorageProperties storageProperties, AmazonS3 amazonS3) {
		if (StorageProperties.StorageType.AWS_S3.equals(storageProperties.getType())) {
			return new AwsS3FileStorageServiceImpl(storageProperties, amazonS3);
		}
		return new LocalFileStorageServiceImpl(storageProperties);
	}
}
