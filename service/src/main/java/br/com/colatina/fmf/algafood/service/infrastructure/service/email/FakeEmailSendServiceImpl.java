package br.com.colatina.fmf.algafood.service.infrastructure.service.email;

import br.com.colatina.fmf.algafood.service.domain.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FakeEmailSendServiceImpl implements EmailSendService {
	@Override
	public void send(Email email) {
		log.info("Sending fake e-mail to {}", email.getRecipients());
	}
}
