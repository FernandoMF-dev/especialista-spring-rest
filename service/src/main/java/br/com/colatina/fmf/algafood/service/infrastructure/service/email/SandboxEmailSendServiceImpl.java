package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.core.email.EmailProperties;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.EmailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class SandboxEmailSendServiceImpl extends SmtpEmailSendServiceImpl {
	public SandboxEmailSendServiceImpl(EmailProperties emailProperties, JavaMailSender mailSender, EmailTemplateProcessor emailTemplateProcessor) {
		super(emailProperties, mailSender, emailTemplateProcessor);
	}

	@Override
	public void send(Email email) {
		try {
			MimeMessage mimeMessage = createMessage(email);
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, emailProperties.getEncoding());
			helper.setTo(emailProperties.getSandbox().getRecipient());
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("infrastructure.error.email.send", e);
		}
	}
}
