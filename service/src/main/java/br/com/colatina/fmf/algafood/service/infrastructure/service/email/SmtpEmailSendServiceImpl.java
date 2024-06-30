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
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SmtpEmailSendServiceImpl implements EmailSendService {
	private final JavaMailSender mailSender;
	private final EmailProperties emailProperties;
	private final Configuration freemarkerConfig;

	@Override
	public void send(Email email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, emailProperties.getEncoding());
			String emailBody = processTemplate(email);

			helper.setFrom(emailProperties.getSender());
			helper.setSubject(email.getSubject());
			helper.setText(emailBody, true);
			helper.setTo(email.getRecipients().toArray(new String[0]));

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("infrastructure.error.email.send", e);
		}
	}

	private String processTemplate(Email email) throws IOException, TemplateException {
		Template template = freemarkerConfig.getTemplate(email.getBody());

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariables());
	}
}
