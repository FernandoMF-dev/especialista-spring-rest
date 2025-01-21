package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;

import java.net.UnknownHostException;

public interface RootEntryPointControllerDocumentation {
	HypermediaModel root();

	HypermediaModel rootApi();

	HypermediaModel rootApiV1();

	HypermediaModel rootApiV2();

	String checkHost() throws UnknownHostException;
}
