package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthorizationConsentController {
	private final RegisteredClientRepository registeredClientRepository;
	private final OAuth2AuthorizationConsentService authorizationConsentService;

	@GetMapping("/oauth2/consent")
	public String consent(Principal principal, Model model,
						  @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
						  @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
						  @RequestParam(OAuth2ParameterNames.STATE) String state) {
		RegisteredClient client = this.registeredClientRepository.findByClientId(clientId);

		if (Objects.isNull(client)) {
			throw new AccessDeniedException(String.format("Client with ID \"%s\" was not found", clientId));
		}

		OAuth2AuthorizationConsent consent = this.authorizationConsentService.findById(client.getId(), principal.getName());

		String[] scopeArray = StringUtils.delimitedListToStringArray(scope, " ");
		Set<String> scopesToBeApprove = new HashSet<>(Set.of(scopeArray));

		Set<String> scopesAlreadyApproved;
		if (Objects.nonNull(consent)) {
			scopesAlreadyApproved = consent.getScopes();
			scopesToBeApprove.removeAll(scopesAlreadyApproved);
		} else {
			scopesAlreadyApproved = Collections.emptySet();
		}

		model.addAttribute("clientId", clientId);
		model.addAttribute("state", state);
		model.addAttribute("principalName", principal.getName());
		model.addAttribute("scopesToBeApprove", scopesToBeApprove);
		model.addAttribute("scopesAlreadyApproved", scopesAlreadyApproved);

		return "oauth2_approval.page";
	}
}
