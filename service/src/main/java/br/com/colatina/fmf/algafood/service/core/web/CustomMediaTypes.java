package br.com.colatina.fmf.algafood.service.core.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;

@UtilityClass
public class CustomMediaTypes {
	public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";

	public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);
}
