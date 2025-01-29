package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.List;

@Configuration
public class AuthorizationServerConfig {
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
		return httpSecurity.build();
	}

	@Bean
	public AuthorizationServerSettings providerSettings(AlgafoodSecurityProperties properties) {
		return AuthorizationServerSettings.builder()
				.issuer(properties.getProviderUrl())
				.build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
		RegisteredClient algafoodBackend = backendRegisteredClient(passwordEncoder);

		return new InMemoryRegisteredClientRepository(List.of(algafoodBackend));
	}

	@Bean
	public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource(JwtKeystoreProperties properties) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, JOSEException {
		Resource jksLocation = properties.getJksLocation();
		InputStream jksFile = jksLocation.getInputStream();
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(jksFile, properties.getJksPassword().toCharArray());

		RSAKey rsaKey = RSAKey.load(keyStore, properties.getKeyPairAlias(), properties.getKeyPairPassword().toCharArray());

		return new ImmutableJWKSet<>(new JWKSet(rsaKey));
	}

	private RegisteredClient backendRegisteredClient(PasswordEncoder passwordEncoder) {
		TokenSettings tokenSettings = TokenSettings.builder()
				.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
				.accessTokenTimeToLive(Duration.ofMinutes(30))
				.build();

		return RegisteredClient.withId("1")
				.clientId("algafood-backend")
				.clientSecret(passwordEncoder.encode("backend123"))
				.clientAuthenticationMethods(set -> set.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope("READ")
				.tokenSettings(tokenSettings)
				.build();
	}
}
