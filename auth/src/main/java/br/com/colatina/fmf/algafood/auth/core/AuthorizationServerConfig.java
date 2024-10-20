package br.com.colatina.fmf.algafood.auth.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final RedisConnectionFactory connectionFactory;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("fmf-algafood-web")
				.secret(passwordEncoder.encode("1234567890"))
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("write", "read")
				.accessTokenValiditySeconds(6 * 60 * 60) // 6 horas. O padrão é 12 horas.
				.refreshTokenValiditySeconds(60 * 24 * 60 * 60) // 60 dias.

				.and()
				.withClient("fmf-algafood-faturamento")
				.secret(passwordEncoder.encode("faturamento123"))
				.authorizedGrantTypes("client_credentials")
				.scopes("read")

				.and()
				.withClient("fmf-algafood-analytics")
				.secret(passwordEncoder.encode("analytics123"))
				.authorizedGrantTypes("authorization_code")
				.scopes("write", "read")
				.redirectUris("http://www.fmf-algafood-analytics.com.br", "http://localhost:63342")

				.and()
				.withClient("fmf-algafood-webadmin")
				.authorizedGrantTypes("implicit")
				.scopes("write", "read")
				.redirectUris("http://www.fmf-algafood-webadmin.com.br", "http://localhost:63342")

				.and()
				.withClient("check-token")
				.secret(passwordEncoder.encode("check123"));
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
//		security.checkTokenAccess("isAuthenticated()");
		security.checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.reuseRefreshTokens(false)
				.tokenStore(redisTokenStore())
				.tokenGranter(tokenGranter(endpoints));
	}

	private TokenStore redisTokenStore() {
		return new RedisTokenStore(connectionFactory);
	}

	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());

		var granters = Arrays.asList(pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

		return new CompositeTokenGranter(granters);
	}
}
