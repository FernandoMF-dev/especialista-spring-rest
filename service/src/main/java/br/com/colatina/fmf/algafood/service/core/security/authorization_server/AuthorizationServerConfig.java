package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtKeystoreProperties jwtKeystoreProperties;
	private final DataSource dataSource;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security.checkTokenAccess("permitAll()")
				.tokenKeyAccess("permitAll()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		var enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(List.of(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.reuseRefreshTokens(false)
				.accessTokenConverter(jwtAccessTokenConverter())
				.tokenEnhancer(enhancerChain)
				.approvalStore(approvalStore(endpoints.getTokenStore()))
				.tokenGranter(tokenGranter(endpoints));
	}

	private ApprovalStore approvalStore(TokenStore tokenStore) {
		var approvalStore = new TokenApprovalStore();
		approvalStore.setTokenStore(tokenStore);
		return approvalStore;
	}

	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
				.keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.RS256)
				.keyID("fmf-algafood-key-id");

		return new JWKSet(builder.build());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		var keyPair = keyPair();
		var jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setKeyPair(keyPair);
		return jwtAccessTokenConverter;
	}

	private KeyPair keyPair() {
		var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeystoreProperties.getJksLocation(), jwtKeystoreProperties.getJksPassword().toCharArray());
		return keyStoreKeyFactory.getKeyPair(jwtKeystoreProperties.getKeyPairAlias(), jwtKeystoreProperties.getKeyPairPassword().toCharArray());
	}

	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());

		var granters = Arrays.asList(pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

		return new CompositeTokenGranter(granters);
	}
}
