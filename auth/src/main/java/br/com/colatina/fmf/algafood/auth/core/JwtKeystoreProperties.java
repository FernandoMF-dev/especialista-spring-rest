package br.com.colatina.fmf.algafood.auth.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties("algafood.jwt.keystore")
@Getter
@Setter
public class JwtKeystoreProperties {
	private String jksLocation;
	private String jksPassword;
	private String keyPairAlias;
	private String keyPairPassword;
}
