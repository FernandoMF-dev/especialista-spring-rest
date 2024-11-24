package br.com.colatina.fmf.algafood.service.core.io;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.util.Base64;

public class Base64ProtocolResolver implements ProtocolResolver, ApplicationListener<ApplicationContextInitializedEvent> {
	@Override
	@Nullable
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		String protocol = "base64:";
		if (location.startsWith(protocol)) {
			byte[] decodedResource = Base64.getDecoder().decode(location.substring(protocol.length()));
			return new ByteArrayResource(decodedResource);
		}

		return null;
	}

	@Override
	public void onApplicationEvent(ApplicationContextInitializedEvent event) {
		event.getApplicationContext().addProtocolResolver(this);
	}
}
