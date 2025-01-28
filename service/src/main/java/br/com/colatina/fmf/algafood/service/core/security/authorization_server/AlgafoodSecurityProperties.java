package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("algafood.auth")
public class AlgafoodSecurityProperties {
	@NotBlank
	private String providerUrl;
}
