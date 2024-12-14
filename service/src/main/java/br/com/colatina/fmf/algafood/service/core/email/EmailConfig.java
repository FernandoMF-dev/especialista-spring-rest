package br.com.colatina.fmf.algafood.service.core.email;

import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.EmailTemplateProcessor;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.FakeEmailSendServiceImpl;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.SandboxEmailSendServiceImpl;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.SmtpEmailSendServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

@Configuration
public class EmailConfig {
	@Bean
	public EmailSendService emailSendService(EmailProperties emailProperties, @Nullable JavaMailSender mailSender,
											 EmailTemplateProcessor emailTemplateProcessor) {
		switch (emailProperties.getType()) {
			case SMTP:
				Assert.notNull(mailSender, "A bean of type JavaMailSender is required for SMTP email type");
				return new SmtpEmailSendServiceImpl(emailProperties, mailSender, emailTemplateProcessor);
			case SANDBOX:
				Assert.notNull(mailSender, "A bean of type JavaMailSender is required for SANDBOX email type");
				return new SandboxEmailSendServiceImpl(emailProperties, mailSender, emailTemplateProcessor);
			case FAKE:
				return new FakeEmailSendServiceImpl(emailTemplateProcessor);
			default:
				return null;
		}
	}
}
