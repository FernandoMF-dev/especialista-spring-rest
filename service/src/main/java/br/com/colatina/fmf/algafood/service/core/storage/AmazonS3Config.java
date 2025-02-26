package br.com.colatina.fmf.algafood.service.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
	@Bean
	public AmazonS3 amazonS3(StorageProperties storageProperties) {
		if (storageProperties.getType() != StorageProperties.StorageType.AWS_S3) {
			return null;
		}
		var accessKey = storageProperties.getAwsS3().getAccessKey();
		var credentials = new BasicAWSCredentials(accessKey.getId(), accessKey.getSecret());

		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getAwsS3().getRegion())
				.build();
	}
}
