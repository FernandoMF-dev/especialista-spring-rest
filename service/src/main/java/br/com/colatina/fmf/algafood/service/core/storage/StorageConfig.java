package br.com.colatina.fmf.algafood.service.core.storage;

import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.infrastructure.service.storage.AwsS3FileStorageServiceImpl;
import br.com.colatina.fmf.algafood.service.infrastructure.service.storage.LocalFileStorageServiceImpl;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Configuration
public class StorageConfig {
	@Bean
	public FileStorageService fileStorageService(StorageProperties storageProperties, @Nullable AmazonS3 amazonS3) {
		if (StorageProperties.StorageType.AWS_S3.equals(storageProperties.getType())) {
			Assert.notNull(amazonS3, "A bean of type AmazonS3 is required for AWS_S3 storage type");
			return new AwsS3FileStorageServiceImpl(storageProperties, amazonS3);
		}
		return new LocalFileStorageServiceImpl(storageProperties);
	}
}
