package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocControllerTags;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.UnknownHostException;

@Tag(name = SpringDocControllerTags.ROOT)
@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface RootEntryPointControllerDocumentation {
	@Operation(summary = "Find root entry point endpoints")
	HypermediaModel root();

	@Operation(summary = "Find root entry point endpoints of the API")
	HypermediaModel rootApi();

	@Operation(summary = "Find root entry point endpoints of the API (version 1)")
	HypermediaModel rootApiV1();

	@Operation(summary = "Find root entry point endpoints of the API (version 2)")
	HypermediaModel rootApiV2();

	@Operation(summary = "Check host address and name")
	String checkHost() throws UnknownHostException;
}
