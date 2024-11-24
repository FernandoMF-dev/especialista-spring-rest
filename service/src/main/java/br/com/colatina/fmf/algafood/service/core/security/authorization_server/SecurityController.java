package br.com.colatina.fmf.algafood.service.core.security.authorization_server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	@GetMapping("/login")
	public String login() {
		return "login.page";
	}

	@GetMapping("/oauth/confirm_access")
	public String approval() {
		return "oauth2_approval.page";
	}
}
