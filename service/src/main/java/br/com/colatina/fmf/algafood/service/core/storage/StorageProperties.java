package br.com.colatina.fmf.algafood.service.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "algafood.storage")
public class StorageProperties {
	private Local local = new Local();
	private AwsS3 awsS3 = new AwsS3();

	@Getter
	@Setter
	public static class Local {
		private Path fileDirectory;
	}

	@Getter
	@Setter
	public static class AwsS3 {
		private AccessKey accessKey = new AccessKey();
		private String bucket;
		private Regions region;
		private String fileDirectory;

		@Getter
		@Setter
		public static class AccessKey {
			private String id;
			private String secret;
		}
	}
}
