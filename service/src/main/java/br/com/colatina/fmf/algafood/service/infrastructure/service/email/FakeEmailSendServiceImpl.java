package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.EmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FakeEmailSendServiceImpl implements EmailSendService {
	protected final EmailTemplateProcessor emailTemplateProcessor;

	@Override
	public void send(Email email) {
		try {
			String emailBody = emailTemplateProcessor.processTemplate(email);
			log.info("[FAKE EMAIL] to {} : {}", email.getRecipients(), emailBody);
		} catch (Exception e) {
			throw new EmailException("infrastructure.error.email.send", e);
		}
	}
}
