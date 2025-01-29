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
import org.springframework.security.config.Customizer;
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
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
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
		return httpSecurity.formLogin(Customizer.withDefaults()).build();
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
		RegisteredClient algafoodWeb = webRegisteredClient(passwordEncoder);
		RegisteredClient algafoodAnalytics = analyticsRegisteredClient(passwordEncoder);

		return new InMemoryRegisteredClientRepository(List.of(algafoodBackend, algafoodWeb, algafoodAnalytics));
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
		TokenSettings tokenSettings = buildTokenSettings(Duration.ofMinutes(30));

		return RegisteredClient.withId("1")
				.clientId("fmf-algafood-backend")
				.clientSecret(passwordEncoder.encode("backend123"))
				.clientAuthenticationMethods(set -> set.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope("READ")
				.tokenSettings(tokenSettings)
				.build();
	}

	private RegisteredClient webRegisteredClient(PasswordEncoder passwordEncoder) {
		TokenSettings tokenSettings = buildTokenSettings(Duration.ofMinutes(15));

		ClientSettings clientSettings = ClientSettings.builder()
				.requireAuthorizationConsent(true)
				.build();

		return RegisteredClient.withId("2")
				.clientId("fmf-algafood-web")
				.clientSecret(passwordEncoder.encode("web123"))
				.clientAuthenticationMethods(set -> set.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.scope("READ").scope("WRITE").scope("DELETE")
				.tokenSettings(tokenSettings)
				.redirectUri("http://www.fmf-algafood-web.com.br")
				.redirectUri("http://127.0.0.1:8080/swagger-ui/index.html/")
				.clientSettings(clientSettings)
				.build();
	}

	private RegisteredClient analyticsRegisteredClient(PasswordEncoder passwordEncoder) {
		TokenSettings tokenSettings = buildTokenSettings(Duration.ofMinutes(20));

		ClientSettings clientSettings = ClientSettings.builder()
				.requireAuthorizationConsent(false)
				.build();

		return RegisteredClient.withId("3")
				.clientId("fmf-algafood-analytics")
				.clientSecret(passwordEncoder.encode("analytics123"))
				.clientAuthenticationMethods(set -> set.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.scope("READ").scope("WRITE")
				.tokenSettings(tokenSettings)
				.redirectUri("http://www.fmf-algafood-analytics.com.br")
				.clientSettings(clientSettings)
				.build();
	}

	private TokenSettings buildTokenSettings(Duration timeToLive) {
		return TokenSettings.builder()
				.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
				.accessTokenTimeToLive(timeToLive)
				.build();
	}
}
