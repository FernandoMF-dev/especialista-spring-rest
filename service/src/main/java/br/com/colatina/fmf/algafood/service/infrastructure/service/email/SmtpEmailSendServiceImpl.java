package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.core.email.EmailProperties;
import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SmtpEmailSendServiceImpl implements EmailSendService {
	private final JavaMailSender mailSender;
	private final EmailProperties emailProperties;

	@Override
	public void send(Email email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, emailProperties.getEncoding());

			helper.setFrom(emailProperties.getSender());
			helper.setSubject(email.getSubject());
			helper.setText(email.getBody(), true);
			helper.setTo(email.getRecipients().toArray(new String[0]));

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("infrastructure.error.email.send", e);
		}
	}
}
