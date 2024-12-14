package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmailTemplateProcessor {
	private final Configuration freemarkerConfig;

	public String processTemplate(EmailSendService.Email email) throws IOException, TemplateException {
		Template template = freemarkerConfig.getTemplate(email.getBody());

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariables());
	}
}
