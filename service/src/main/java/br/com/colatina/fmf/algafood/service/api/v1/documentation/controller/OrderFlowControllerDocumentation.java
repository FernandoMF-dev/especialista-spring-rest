package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface OrderFlowControllerDocumentation {
	ResponseEntity<Void> confirm(String orderUuid);

	ResponseEntity<Void> deliver(String orderUuid);

	ResponseEntity<Void> cancel(String orderUuid);
}
