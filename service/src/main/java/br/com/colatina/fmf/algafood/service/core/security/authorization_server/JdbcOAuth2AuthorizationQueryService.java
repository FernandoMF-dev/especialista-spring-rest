package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public class JdbcOAuth2AuthorizationQueryService implements OAuth2AuthorizationQueryService {
	private static final String LIST_AUTHORIZED_CLIENTS = "SELECT rc.* FROM oauth2_authorization_consent c " +
			"INNER JOIN oauth2_registered_client rc ON rc.id = c.registered_client_id " +
			"WHERE c.principal_name = ?";

	private final JdbcOperations jdbcOperations;
	private final RowMapper<RegisteredClient> registeredClientRowMapper;

	public JdbcOAuth2AuthorizationQueryService(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
		this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
	}

	@Override
	public List<RegisteredClient> listClientsWithConsent(String principalName) {
		return this.jdbcOperations.query(LIST_AUTHORIZED_CLIENTS, registeredClientRowMapper, principalName);
	}
}
