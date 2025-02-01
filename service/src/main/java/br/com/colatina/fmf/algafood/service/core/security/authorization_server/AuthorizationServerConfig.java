package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.repository.AppUserRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AuthorizationServerConfig {
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		authorizationServerConfigurer.authorizationEndpoint(customizer -> customizer.consentPage("/oauth2/consent"));
		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

		httpSecurity.securityMatcher(endpointsMatcher)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.formLogin(Customizer.withDefaults())
				.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
				.apply(authorizationServerConfigurer);

		return httpSecurity.build();
	}

	@Bean
	public AuthorizationServerSettings providerSettings(AlgafoodSecurityProperties properties) {
		return AuthorizationServerSettings.builder()
				.issuer(properties.getProviderUrl())
				.build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcOperations jdbcOperations) {
		return new JdbcRegisteredClientRepository(jdbcOperations);
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

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer(AppUserRepository appUserRepository) {
		return context -> {
			Authentication authentication = context.getPrincipal();
			if (authentication.getPrincipal() instanceof User user) {
				AppUser appUser = appUserRepository.findByEmailAndExcludedIsFalse(user.getUsername()).orElseThrow();

				Set<String> authorities = new HashSet<>();
				for (GrantedAuthority authority : user.getAuthorities()) {
					authorities.add(authority.getAuthority());
				}

				context.getClaims().claim("userId", appUser.getId().toString());
				context.getClaims().claim("fullName", appUser.getName());
				context.getClaims().claim("authorities", authorities);
			}
		};
	}

	@Bean
	public OAuth2AuthorizationConsentService consentService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationQueryService(jdbcOperations, registeredClientRepository);
	}
}
