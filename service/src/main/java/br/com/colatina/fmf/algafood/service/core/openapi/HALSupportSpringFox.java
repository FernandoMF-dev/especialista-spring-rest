package br.com.colatina.fmf.algafood.service.core.openapi;

import org.springframework.context.ApplicationListener;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;

@Component
public class HALSupportSpringFox implements ApplicationListener<ObjectMapperConfigured> {
	@Override
	public void onApplicationEvent(ObjectMapperConfigured event) {
		event.getObjectMapper().registerModule(new Jackson2HalModule());
	}
}
