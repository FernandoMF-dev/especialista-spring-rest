package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwkSetController {
	private final JWKSet jwkSet;

	@GetMapping("/.well-known/jwks.json")
	public Map<String, Object> keys() {
		log.debug("Retornando JWK Set");
		return this.jwkSet.toJSONObject();
	}
}
