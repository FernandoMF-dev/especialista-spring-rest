package br.com.colatina.fmf.algafood.service.core.email;

import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.FakeEmailSendServiceImpl;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.SandboxEmailSendServiceImpl;
import br.com.colatina.fmf.algafood.service.infrastructure.service.email.SmtpEmailSendServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {
	@Bean
	public EmailSendService emailSendService(EmailProperties emailProperties, JavaMailSender mailSender,
											 freemarker.template.Configuration freemarkerConfig) {
		switch (emailProperties.getType()) {
			case SMTP:
				return new SmtpEmailSendServiceImpl(emailProperties, mailSender, freemarkerConfig);
			case SANDBOX:
				return new SandboxEmailSendServiceImpl(emailProperties, mailSender, freemarkerConfig);
			default:
				return new FakeEmailSendServiceImpl(emailProperties, mailSender, freemarkerConfig);
		}
	}
}
