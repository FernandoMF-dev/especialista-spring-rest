package br.com.colatina.fmf.algafood.service.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "algafood.email")
public class EmailProperties {
	@NotNull
	private String sender;
	@NotNull
	private String encoding;
	private Sandbox sandbox = new Sandbox();
	private EmailType type = EmailType.FAKE;

	public enum EmailType {
		FAKE, SMTP, SANDBOX
	}

	@Getter
	@Setter
	public static class Sandbox {
		private String recipient;
	}
}
