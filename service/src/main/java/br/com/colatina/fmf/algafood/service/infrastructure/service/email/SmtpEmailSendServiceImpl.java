package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.core.email.EmailProperties;
import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@RequiredArgsConstructor
public class SmtpEmailSendServiceImpl implements EmailSendService {
	protected final EmailProperties emailProperties;
	protected final JavaMailSender mailSender;
	protected final Configuration freemarkerConfig;

	@Override
	public void send(Email email) {
		try {
			MimeMessage mimeMessage = createMessage(email);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("infrastructure.error.email.send", e);
		}
	}

	protected MimeMessage createMessage(Email email) throws IOException, TemplateException, MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, emailProperties.getEncoding());
		String emailBody = processTemplate(email);

		helper.setFrom(emailProperties.getSender());
		helper.setSubject(email.getSubject());
		helper.setText(emailBody, true);
		helper.setTo(email.getRecipients().toArray(new String[0]));
		return mimeMessage;
	}

	protected String processTemplate(Email email) throws IOException, TemplateException {
		Template template = freemarkerConfig.getTemplate(email.getBody());

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariables());
	}
}
