package br.com.colatina.fmf.algafood.auth.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;

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
				.withClient("check-token")
				.secret(passwordEncoder.encode("check123"));
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
//		security.checkTokenAccess("isAuthenticated()");
		security.checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.reuseRefreshTokens(false);
	}
}
