package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Api(tags = SpringFoxControllerTags.ROOT)
public interface RootEntryPointControllerDocumentation {
	@ApiOperation("Find root entry point endpoints")
	@ApiResponse(responseCode = "200", description = "Root entry point endpoints retrieved")
	HypermediaModel root();

	@ApiOperation("Find root entry point endpoints of the API")
	@ApiResponse(responseCode = "200", description = "Root entry point endpoints of the API retrieved")
	HypermediaModel rootApi();

	@ApiOperation("Find root entry point endpoints of the API (version 1)")
	@ApiResponse(responseCode = "200", description = "Root entry point endpoints of the API  (version 1) retrieved")
	HypermediaModel rootApiV1();

	@ApiOperation("Find root entry point endpoints of the API (version 2)")
	@ApiResponse(responseCode = "200", description = "Root entry point endpoints of the API  (version 2) retrieved")
	HypermediaModel rootApiV2();
}
