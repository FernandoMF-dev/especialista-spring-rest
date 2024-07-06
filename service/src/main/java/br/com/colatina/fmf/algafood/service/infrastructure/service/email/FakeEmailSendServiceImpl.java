package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.core.email.EmailProperties;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.EmailException;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class FakeEmailSendServiceImpl extends SmtpEmailSendServiceImpl {
	public FakeEmailSendServiceImpl(EmailProperties emailProperties, JavaMailSender mailSender, Configuration freemarkerConfig) {
		super(emailProperties, mailSender, freemarkerConfig);
	}

	@Override
	public void send(Email email) {
		try {
			String emailBody = processTemplate(email);
			log.info("[FAKE EMAIL] to {} : {}", email.getRecipients(), emailBody);
		} catch (Exception e) {
			throw new EmailException("infrastructure.error.email.send", e);
		}
	}
}
