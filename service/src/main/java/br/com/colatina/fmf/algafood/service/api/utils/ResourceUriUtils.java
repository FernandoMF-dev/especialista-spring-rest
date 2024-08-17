package br.com.colatina.fmf.algafood.service.api.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;

@UtilityClass
public class ResourceUriUtils {
	public static void addUriInResponseHeader(Object resourceId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(resourceId)
				.toUri();

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = Objects.requireNonNull(requestAttributes).getResponse();

		Objects.requireNonNull(response).setHeader(HttpHeaders.LOCATION, uri.toString());
	}
}
