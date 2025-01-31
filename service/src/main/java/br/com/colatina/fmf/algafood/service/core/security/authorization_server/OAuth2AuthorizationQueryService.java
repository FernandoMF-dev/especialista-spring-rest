package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public interface OAuth2AuthorizationQueryService {
	List<RegisteredClient> listClientsWithConsent(String principalName);
}
