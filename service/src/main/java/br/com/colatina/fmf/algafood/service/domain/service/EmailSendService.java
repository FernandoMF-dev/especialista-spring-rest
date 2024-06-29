package br.com.colatina.fmf.algafood.service.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EmailSendService {
	void send(Email email);

	@Getter
	@Builder
	class Email {
		private String subject;
		private String body;
		private Set<String> recipients;
	}
}
