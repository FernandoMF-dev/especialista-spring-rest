package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@Component
@ConfigurationProperties("algafood.jwt.keystore")
@Getter
@Setter
public class JwtKeystoreProperties {
	@NotNull
	private Resource jksLocation;
	@NotBlank
	private String jksPassword;
	@NotBlank
	private String keyPairAlias;
	private String keyPairPassword;
}


