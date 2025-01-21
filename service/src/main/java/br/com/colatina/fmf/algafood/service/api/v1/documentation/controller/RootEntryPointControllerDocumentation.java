package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.net.UnknownHostException;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RootEntryPointControllerDocumentation {
	HypermediaModel root();

	HypermediaModel rootApi();

	HypermediaModel rootApiV1();

	HypermediaModel rootApiV2();

	String checkHost() throws UnknownHostException;
}
