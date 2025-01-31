package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AuthorizedClientsController {
	private final OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService;
	private final RegisteredClientRepository registeredClientRepository;
	private final OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService;
	private final OAuth2AuthorizationService oAuth2AuthorizationService;

	@GetMapping("/oauth2/authorized-clients")
	public String clientList(Principal principal, Model model) {
		List<RegisteredClient> clients = oAuth2AuthorizationQueryService.listClientsWithConsent(principal.getName());
		model.addAttribute("clients", clients);
		return "authorized-clients.page";
	}

	@PostMapping("/oauth2/authorized-clients/revoke")
	public String revoke(Principal principal, Model model, @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId) {
		RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

		if (Objects.isNull(registeredClient)) {
			throw new AccessDeniedException(String.format("Client with ID \"%s\" was not found", clientId));
		}

		OAuth2AuthorizationConsent consent = oAuth2AuthorizationConsentService.findById(registeredClient.getId(), principal.getName());
		if (Objects.nonNull(consent)) {
			oAuth2AuthorizationConsentService.remove(consent);
		}

		List<OAuth2Authorization> authorizations = oAuth2AuthorizationQueryService.listAuthorizations(principal.getName(), registeredClient.getId());
		for (OAuth2Authorization authorization : authorizations) {
			oAuth2AuthorizationService.remove(authorization);
		}

		return "redirect:/oauth2/authorized-clients";
	}
}
