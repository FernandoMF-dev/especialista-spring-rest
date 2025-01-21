package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface PaymentMethodControllerDocumentation {
	ResponseEntity<CollectionModel<PaymentMethodDto>> findAll(ServletWebRequest request);

	ResponseEntity<PaymentMethodDto> findById(Long id, ServletWebRequest request);

	ResponseEntity<PaymentMethodDto> insert(PaymentMethodDto dto);

	ResponseEntity<PaymentMethodDto> update(Long id, PaymentMethodDto dto);

	ResponseEntity<Void> delete(Long id);
}
